package com.example.jay3165.garglibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AllBookdetailsActivity extends AppCompatActivity implements AllBookdetailsAdapter.Clickable {
    private RecyclerView rvBookdetails;
    private Context mContext;
    private Activity mActivity;
    String s;
    String pid,pname,pprice,qty,pimg;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;
    TextView t1;
    TextView txtpname,txtpprice;
    ImageView img;
    Button btn1,btn2;
    String strcounter="1";
    Context context=this;

    public static int id=0;

    int j =0 ;
    private LinearLayout linearLayout ;
    private RecyclerView.LayoutManager bookdetailsActivityLayout;
    ArrayList<bookdetails> bookdetailsArrayList=new ArrayList<>();
    AllBookdetailsAdapter.Clickable clickable=this;

    int minteger= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bookdetails);
        txtpname=(TextView)findViewById(R.id.text1_bookdetails);
        txtpprice=(TextView)findViewById(R.id.txt_showprice);
        img=(ImageView)findViewById(R.id.imageView_bookimg);

        rvBookdetails=(RecyclerView)findViewById(R.id.rvBookdetails);
        linearLayout=(LinearLayout)findViewById(R.id.linear);
        mContext = getApplicationContext();
        mActivity = AllBookdetailsActivity.this;
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_all_bookdetails);
        t1=(TextView)findViewById(R.id.txt_bookquant);


        btn1=(Button)findViewById(R.id.btn_plus);

        btn2=(Button)findViewById(R.id.btn_minus);



        bookdetailsActivityLayout=new LinearLayoutManager(this);
        rvBookdetails.setLayoutManager(bookdetailsActivityLayout);
        s = getIntent().getStringExtra("p_id");


    }

    public void increaseInteger(View view) {
        if(minteger<10) {
            minteger = minteger + 1;
            display(minteger);
        }

    }
    public void decreaseInteger(View view) {

    TextView t1=(TextView)findViewById(R.id.txt_bookquant);
        btn1=(Button)findViewById(R.id.btn_plus);
        btn2=(Button)findViewById(R.id.btn_minus);
       if (view==btn2){
           if (minteger>1)
               minteger--;
           strcounter=Integer.toString(minteger);
           t1.setText(strcounter);
       }

    }
    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.txt_bookquant);
        displayInteger.setText("" + number);
        strcounter = ""+number;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (bookdetailsArrayList.size() == 0) {
            BookdetailsData();
        }
    }
    public void BookdetailsData(){
        AsyncHttpClient mClient=new AsyncHttpClient();
        mClient.addHeader("Accept","application/json");
        mClient.addHeader("Content-Type","application/json");
        mClient.get(this,"http://khedutstore.com/garglibrary/ws/products_byid.php?p_id="+s,new JsonHttpResponseHandler(){
            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String url=response.getString("img_url");
                    JSONArray mJsonArray = response.getJSONArray("details");
                    JSONObject mJsonObject;
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);

                        int discount=(Integer.parseInt(mJsonObject.getString("p_price")) * Integer.parseInt(mJsonObject.getString("p_discount")))/100;
                        int newprice=Integer.parseInt(mJsonObject.getString("p_price")) - discount;
                        Log.i("jjjjjjjj",mJsonObject.getString("p_price"));

                        Log.i("lllllll",mJsonObject.getString("p_discount"));
                        Log.i("discount",discount+"");

                        bookdetailsArrayList.add(new bookdetails(mJsonObject.getString("p_id"),mJsonObject.getString("p_name"),mJsonObject.getString("p_price"),mJsonObject.getString("p_brand_name"),mJsonObject.getString("main_cat_id"),mJsonObject.getString("main_sub_id"),mJsonObject.getString("sub_cat_id"),mJsonObject.getString("p_specification"),url+mJsonObject.getString("p_image"),mJsonObject.getString("p_discount"),mJsonObject.getString("p_cat_id"),mJsonObject.getString("p_status"),mJsonObject.getString("p_stock"),newprice+""));
                        Log.i("oooooooo",newprice+"");
                        Log.e("Array Length", bookdetailsArrayList.size() + "");
                        rvBookdetails.setAdapter(new AllBookdetailsAdapter(bookdetailsArrayList, getApplicationContext(), clickable));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(AllBookdetailsActivity.this, "Loading", "Please Wait", true, false);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
            }
        );
    }

    @Override
    public void onBackPressed() {

        if (j==1){
            mPopupWindow.dismiss();
            j=0;
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void click(int position) {


            j=1;

            DBHandler db = new DBHandler(AllBookdetailsActivity.this);
            Log.i("strcounter", strcounter);
            Log.i("price", bookdetailsArrayList.get(0).getP_newprice());

            int total = Integer.parseInt(strcounter) * Integer.parseInt(bookdetailsArrayList.get(0).getP_newprice());
            Log.i("value", total + "");
            db.addproduct(new BookAddToCart(++id, bookdetailsArrayList.get(0).getP_name()
                    , bookdetailsArrayList.get(0).getP_image(), bookdetailsArrayList.get(0).getP_newprice()
                    , minteger+"", total + ""));
        Toast.makeText(getApplicationContext(),"Product Add into Cart..",Toast.LENGTH_SHORT).show();

            Button b1, b2;


            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.custome_layout_popup, null);
            b1 = (Button) customView.findViewById(R.id.btncontinueshopping);
            b2 = (Button) customView.findViewById(R.id.btngotocart);
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(AllBookdetailsActivity.this, AllCartActivity.class);

                    startActivity(i);

                }
            });
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(AllBookdetailsActivity.this, AllCourseActivity.class);
                    startActivity(i);
                }
            });

            mPopupWindow = new PopupWindow(
                    customView,
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
            // Toast.makeText(this,bookdetailsArrayList.get(position).getP_name(),Toast.LENGTH_SHORT).show();



    }


}
