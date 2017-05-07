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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class OtpActivity extends AppCompatActivity {
    Button b;
    EditText e1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        b = (Button) findViewById(R.id.btnok);
        e1 = (EditText) findViewById(R.id.etdotp);
        sharedPreferences = getSharedPreferences(Loginpref.MY_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        s=getIntent().getStringExtra("otpis");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = e1.getText().toString().trim();

                if (otp.equals(s)){
                    if (!otp.isEmpty()){
                        registerconfirm();
                    }else {
                        Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    e1.setError("OTP Does Not Match");
                }

            }
        });
    }


    private void registerconfirm(){
        RequestParams params=new RequestParams();
        params.put("otp",e1.getText().toString().trim());

        AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                Log.i("rconfirm Response",s);
                try {
                    JSONObject jObj = new JSONObject(s);
                    boolean status = jObj.getBoolean("status");
                    if (status == true) {

                        String e = jObj.getString("details");
                        Toast.makeText(getApplicationContext(), "OTP Verified . Try login now!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                OtpActivity.this,
                                login_activity.class);

                        startActivity(intent);

                        finish();
                    } else {
                        String errormsg = jObj.getString("details");
                        Toast.makeText(getApplicationContext(), "try/else/error" +
                                errormsg, Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                e.printStackTrace();
            }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(OtpActivity.this, "Server Error...", Toast.LENGTH_SHORT).show();

            }
        };

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.post("http://khedutstore.com/garglibrary/ws/register_confirmation.php",params,handler);
        Log.e("URL", AsyncHttpClient.getUrlWithQueryString(true,"http://khedutstore.com/garglibrary/ws/register_confirmation.php",params));
    }

}
