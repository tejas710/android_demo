package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by jay3165 on 2/28/2017.
 */

public class login_activity extends AppCompatActivity {
    private static final String TAG= login_activity.class.getSimpleName();
    EditText etmail, etpass;
    Button btnlogin,btnregister,btnforgotpass;

    String mail1, pass1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        sharedPreferences = getSharedPreferences(Loginpref.MY_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnlogin=(Button)findViewById(R.id.btn_signin);
        btnregister=(Button)findViewById(R.id.btn_register_design_login);
        btnforgotpass=(Button)findViewById(R.id.btn_forgotpass_design_login);
        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(login_activity.this,ForgotPassword.class);
               i.putExtra("email",etmail.getText().toString());
                i.putExtra("mob",Loginpref.MOB);
                startActivity(i);


            }
        });
        etmail=(EditText)findViewById(R.id.editText_design_login);
        etpass=(EditText)findViewById(R.id.editText2_design_login);
        if(sharedPreferences.getBoolean(Loginpref.ISUSERLIOGIN, false)){
            Intent intent = new Intent(this, AllCourseActivity.class);
            startActivity(intent);
        }
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(login_activity.this,Registration_Activity.class);
                startActivity(i);
            }
        });
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = etmail.getText().toString().trim();
                    String pass = etpass.getText().toString().trim();
                    if (email.isEmpty()) {
                        etmail.setError("Enter Valid Email");
                    } else if (pass.isEmpty()) {
                        etpass.setError("Enter Valid Password");
                    } else {
                    /*Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);*/
                        checkLogin(email, pass);
                    }
                }
            });

    }
    private void checkLogin(final String email, String pass){
        RequestParams params = new RequestParams();
        params.put("username", email);
        params.put("password", pass);
        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                Log.i(TAG, "onSuccess: " + s);
                try {
                    JSONObject object = new JSONObject(s);
                    int status = object.getInt("status");
                    if (status==1) {
                        JSONObject jsonObject = object.getJSONObject("details");
                        editor.putString(Loginpref.ID,jsonObject.getString("client_id"));
                        editor.putString(Loginpref.NAME, jsonObject.getString("client_name"));
                        editor.putString(Loginpref.EMAIL, jsonObject.getString("client_email"));
                        editor.putString(Loginpref.ADDRESS, jsonObject.getString("client_address"));
                        editor.putString(Loginpref.MOB, jsonObject.getString("client_mob"));
                        editor.putString(Loginpref.CITY, jsonObject.getString("client_city"));
                        editor.putString(Loginpref.STATE, jsonObject.getString("client_state"));
                        editor.putString(Loginpref.ZIP, jsonObject.getString("client_zip"));

//                        editor.putString(Loginpref.PASSWORD, jsonObject.getString("client_password"));
                        editor.putBoolean(Loginpref.ISUSERLIOGIN, true);
                        editor.apply();
                        Intent i = new Intent(login_activity.this, AllCourseActivity.class);

                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Login Successfully..", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String msg = object.getString("error_msg");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Wrong Email and password..", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        };
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.post("http://khedutstore.com/garglibrary/ws/login.php",params,handler);
        Log.e("URL", AsyncHttpClient.getUrlWithQueryString(true,"http://khedutstore.com/garglibrary/ws/login.php",params));

    }

}
