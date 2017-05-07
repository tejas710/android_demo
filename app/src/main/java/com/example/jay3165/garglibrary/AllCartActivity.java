package com.example.jay3165.garglibrary;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AllCartActivity extends AppCompatActivity implements AllCartAdapter.Clickable{
    private RecyclerView rvCart;
    private RecyclerView.LayoutManager cartActivityLayout;
    ArrayList<BookAddToCart> cartArrayList=new ArrayList<>();
    AllCartAdapter.Clickable clickable=this;
    String s,p=null;
    DBHandler dbHandler = new DBHandler(this);
    AllCartAdapter allCartAdapter;
    TextView textView,gtotal,gqty;
    Button checkout;
    Button delete;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

   int t,q;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cart);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));


        sharedPreferences = getSharedPreferences(Loginpref.MY_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gtotal=(TextView)findViewById(R.id.txt_totalprice);
        gqty=(TextView)findViewById(R.id.txt_totalqty);
        checkout=(Button)findViewById(R.id.button2);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder pID = new StringBuilder();
                StringBuilder price=new StringBuilder();
                StringBuilder qty=new StringBuilder();
                StringBuilder total=new StringBuilder();
                StringBuilder pName=new StringBuilder();


                for (BookAddToCart cart : cartArrayList){

                    pID.append(","+cart.getP_id());
                    pName.append(","+cart.getP_name());



                    price.append(","+cart.getP_price());
                    qty.append(","+cart.getP_qty());




                }

                editor.putString(Loginpref.PID,pID.substring(1));
                editor.putString(Loginpref.PNAME,pName.substring(1));
                editor.putString(Loginpref.PRICE,price.substring(1));
                editor.putString(Loginpref.QTY,qty.substring(1));

                editor.apply();


                if(sharedPreferences.getBoolean(Loginpref.ISUSERLIOGIN, false)){
                    Intent i=new Intent(AllCartActivity.this,Checkout.class);
                    i.putExtra("grandtotal",gtotal.getText().toString());

                    startActivity(i);
                }else {
                    Intent i=new Intent(AllCartActivity.this,login_activity.class);
                    startActivity(i);
                }
            }
        });

        cartArrayList = dbHandler.getAllBookDetails();
        rvCart = (RecyclerView) findViewById(R.id.rvCart);

        cartActivityLayout = new LinearLayoutManager(this);
        rvCart.setLayoutManager(cartActivityLayout);
        allCartAdapter= new AllCartAdapter(cartArrayList,getApplicationContext(),this);

        // RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());


        rvCart.setLayoutManager(layoutManager);
        rvCart.setItemAnimator(new DefaultItemAnimator());
        rvCart.setAdapter(allCartAdapter);








        //textView = (TextView)findViewById(R.id.txt_totalprice);
        //textView.setText("₹"+cartArrayList.get(0).getP_price());
        for (int i=0;i<cartArrayList.size();++i)
        {
             t+=Integer.parseInt(cartArrayList.get(i).getP_total());
            q+=Integer.parseInt(cartArrayList.get(i).getP_qty().toString());

        }
        gtotal.setText(t+"");
        gqty.setText(q+"");





    }


    @Override
    public void ivClick(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AllCartActivity.this);
        alertDialogBuilder.setIconAttribute(android.R.attr.alertDialogIcon);
        alertDialogBuilder.setTitle("Alert Dialog");
        alertDialogBuilder.setMessage("Do you want to Delete Cart?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dbHandler.deleteproduct(cartArrayList.get(position));
                cartArrayList=dbHandler.getAllBookDetails();
                if (cartArrayList.size() == 0){
                    NotificationManager nMgr = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    nMgr.cancel(0);
                    Intent delete = new Intent(AllCartActivity.this,AllCourseActivity.class);
                    delete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(delete);

                }else {
                    Intent delete = new Intent(AllCartActivity.this,AllCartActivity.class);
                    delete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(delete);

                }
                    finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
        public void click(int position) {


        }
    }
