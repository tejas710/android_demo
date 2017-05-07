package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
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

import cz.msebera.android.httpclient.Header;

public class ForgotPassword extends AppCompatActivity {
    EditText text1;
    String s,p;
    private ProgressDialog pDialog;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        s = getIntent().getStringExtra("email");
        p=getIntent().getStringExtra("mob");
        text1=(EditText)findViewById(R.id.editemail);

        button=(Button)findViewById(R.id.btnforgetpass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eml=text1.getText().toString().trim();
                if (!eml.isEmpty()){
                    forgetpass(eml);
                }else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private void forgetpass(final String eml){
        RequestParams params=new RequestParams();

        params.put("email",eml);
        params.put("action","forgotPassword");
        AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                showDialog();
//            }
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                hideDialog();
//            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                Log.i("Forget Response",s);
                try {
                    JSONObject jObj = new JSONObject(s);
                boolean status = jObj.getBoolean("status");
                    if (status==true){

                        String e=jObj.getString("details");
                        Toast.makeText(getApplicationContext(), "User successfully ForgetPassword . Try login now!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                ForgotPassword.this,
                                Otpfor_forgetpass.class);
                       intent.putExtra("otp",e);
                        intent.putExtra("mobileoremail",text1.getText().toString());
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
                Toast.makeText(ForgotPassword.this, "Server Error...", Toast.LENGTH_SHORT).show();

            }
        };
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.post("http://khedutstore.com/garglibrary/ws/forgotpass.php",params,handler);
        Log.e("URL", AsyncHttpClient.getUrlWithQueryString(true,"http://khedutstore.com/garglibrary/ws/forgotpass.php",params));
    }
//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.setMessage("Loading...");
//        pDialog.show();
//    }
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }

}
