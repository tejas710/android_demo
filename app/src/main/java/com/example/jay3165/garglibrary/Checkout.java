package com.example.jay3165.garglibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Checkout extends AppCompatActivity {
EditText editname,editadd,editcity,editstate,editzip,editmob;
    Button proceed;
    ArrayList<bookdetails>bookdetailsArrayList=new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        sharedPreferences = getSharedPreferences(Loginpref.MY_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
       editname=(EditText)findViewById(R.id.edtname);
        editadd=(EditText)findViewById(R.id.edtaddress);
        editcity=(EditText)findViewById(R.id.edtcity);
        editstate=(EditText)findViewById(R.id.edtstate);
        editzip=(EditText)findViewById(R.id.edtzip);
        editmob=(EditText)findViewById(R.id.edtmob);

        proceed=(Button)findViewById(R.id.btnproceed);
        s=getIntent().getStringExtra("grandtotal");




      editname.setText(sharedPreferences.getString(Loginpref.NAME,""));
        editadd.setText(sharedPreferences.getString(Loginpref.ADDRESS,""));
        editcity.setText(sharedPreferences.getString(Loginpref.CITY,""));
       editstate.setText(sharedPreferences.getString(Loginpref.STATE,""));
        editzip.setText(sharedPreferences.getString(Loginpref.ZIP,""));
        editmob.setText(sharedPreferences.getString(Loginpref.MOB,""));


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params=new RequestParams();
                params.put("p_id",sharedPreferences.getString(Loginpref.PID,""));
                params.put("p_name",sharedPreferences.getString(Loginpref.PNAME,""));
                params.put("cart_total",s);
                Log.i("kkkkkkkkkkk",s);
                params.put("price",sharedPreferences.getString(Loginpref.PRICE,""));
                params.put("qty",sharedPreferences.getString(Loginpref.QTY,""));
                params.put("u_id",sharedPreferences.getString(Loginpref.ID,""));
                params.put("shipp_name",editname.getText().toString());
                params.put("shipp_addr",editadd.getText().toString());
                params.put("shipp_city",editcity.getText().toString());
                params.put("shipp_state",editstate.getText().toString());
                params.put("shipp_zip",editzip.getText().toString());
                params.put("shipp_mob",editmob.getText().toString());


                AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String s = new String(bytes);
                        Log.i("Proceed Response",s);
                        try{
                            JSONObject jObj = new JSONObject(s);
                            boolean status = jObj.getBoolean("status");
                            if (status==true){

                                String e=jObj.getString("details");

                                Toast.makeText(getApplicationContext(), "order placed Successfully...", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(Checkout.this,AllCourseActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                String errormsg=jObj.getString("details");
                                Toast.makeText(getApplicationContext(), "try/else/error" +
                                        errormsg, Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(Checkout.this, "Server Error...", Toast.LENGTH_SHORT).show();

                    }
                };
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(50000);
                client.post("http://khedutstore.com/garglibrary/ws/cart.php",params,handler);
                Log.e("URL", AsyncHttpClient.getUrlWithQueryString(true,"http://khedutstore.com/garglibrary/ws/cart.php",params));
            }
        });

    }
}
