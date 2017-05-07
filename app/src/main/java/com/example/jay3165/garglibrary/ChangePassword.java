package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
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

public class ChangePassword extends AppCompatActivity {
Button changepass;
    EditText etold,etnew,etconfirm;
    private ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        sharedPreferences = getSharedPreferences(Loginpref.MY_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        etold=(EditText)findViewById(R.id.edtoldpass);
        etnew=(EditText)findViewById(R.id.edtnewchangepass);
        etconfirm=(EditText)findViewById(R.id.edtconfirmpass);
        changepass=(Button)findViewById(R.id.btnchangepass);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpass=etold.getText().toString().trim();
                String newpass=etnew.getText().toString().trim();
                String confirmpass=etconfirm.getText().toString().trim();

                if(confirmpass.equals(newpass)){
                    if (!oldpass.isEmpty() && !newpass.isEmpty() && !confirmpass.isEmpty()){
                        changepassword();
                    }else {
                        Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Password Does Not Match",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void changepassword(){
        RequestParams params=new RequestParams();
        params.put("password",etold.getText().toString());
        params.put("u_id",""+ sharedPreferences.getString(Loginpref.ID,"") );
        params.put("new_pass",etnew.getText().toString());

        AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                Log.i("Changepassword Response",s);
                try {
                    JSONObject jObj = new JSONObject(s);
                    boolean status = jObj.getBoolean("status");
                    if (status==true){

                        String e=jObj.getString("details");
                        Toast.makeText(getApplicationContext(), "User successfully ChangePassword . Try login now!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                ChangePassword.this,
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
                Toast.makeText(ChangePassword.this, "Server Error...", Toast.LENGTH_SHORT).show();
            }
        };

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.post("http://khedutstore.com/garglibrary/ws/changepass.php",params,handler);
        Log.e("URL", AsyncHttpClient.getUrlWithQueryString(true,"http://khedutstore.com/garglibrary/ws/changepass.php",params));

    }
}
