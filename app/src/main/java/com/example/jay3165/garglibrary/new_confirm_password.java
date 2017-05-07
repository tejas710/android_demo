package com.example.jay3165.garglibrary;

import android.content.Intent;
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

public class new_confirm_password extends AppCompatActivity {

    EditText edtnewpass,edtcpass;
    Button ok;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_confirm_password);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        edtnewpass=(EditText)findViewById(R.id.edittext_newpass);
        edtcpass=(EditText)findViewById(R.id.edittext_confirmpass);
        ok=(Button)findViewById(R.id.btn_forgetpass);
        s=getIntent().getStringExtra("mobile");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newpassword=edtnewpass.getText().toString().trim();
                String confirmpassword=edtcpass.getText().toString().trim();

                if (confirmpassword.equals(newpassword)){
                    if (!newpassword.isEmpty() && !confirmpassword.isEmpty()){
                       forgetpass(newpassword);
                    }else {
                        Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Password Does Not Match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void forgetpass(final String newpassword){
        RequestParams params=new RequestParams();
        params.put("action","resetPassword");
        params.put("newPassword",newpassword);
        params.put("mobileNumber",s);
        Log.i("value get",s+"");


        AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler(){

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
                                new_confirm_password.this,
                                login_activity.class);

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
                Toast.makeText(new_confirm_password.this, "Server Error...", Toast.LENGTH_SHORT).show();

            }
        };
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.post("http://khedutstore.com/garglibrary/ws/forgotpass.php",params,handler);
        Log.e("URL", AsyncHttpClient.getUrlWithQueryString(true,"http://khedutstore.com/garglibrary/ws/forgotpass.php",params));
    }




}
