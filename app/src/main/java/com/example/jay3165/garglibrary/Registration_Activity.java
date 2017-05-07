package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Registration_Activity extends AppCompatActivity {
    Button register;
     EditText name;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText zip;
    private EditText contact;
    private EditText email;
    private EditText password;
    private EditText cpass;
    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        name=(EditText)findViewById(R.id.edittext_name);
        address=(EditText)findViewById(R.id.edittext_address);
        city=(EditText)findViewById(R.id.edittext_city);
        state=(EditText)findViewById(R.id.edittext_state);
        zip=(EditText)findViewById(R.id.edittext_pincode);
        contact=(EditText)findViewById(R.id.edittext_contactno);
        email=(EditText)findViewById(R.id.edittext_email);
        password=(EditText)findViewById(R.id.edittext_pass);
        cpass=(EditText)findViewById(R.id.edittext_confirmpass);
        register=(Button)findViewById(R.id.btnsubmit);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = name.getText().toString().trim();
                String add = address.getText().toString().trim();
                String cty = city.getText().toString().trim();
                String stt = state.getText().toString().trim();
                String pincode = zip.getText().toString().trim();
                String contct = contact.getText().toString().trim();
                String eml = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String confirmpass = cpass.getText().toString().trim();
                String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                if (confirmpass.equals(pass)) {


                    if (!fullname.isEmpty() && !add.isEmpty() && !cty.isEmpty() && !stt.isEmpty() && !pincode.isEmpty() && !contct.isEmpty() && !eml.isEmpty() && !pass.isEmpty() && !confirmpass.isEmpty() && eml.matches(emailPattern)) {
                        registeruser(fullname, add, cty, stt, pincode, contct, eml, pass, confirmpass);

                    } else {
                       Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Password Does Not Match",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private void registeruser(final String fullname,final String add,final String cty,final String stt,final String pincode,final String contct,final String eml,final String pass,final String confirmpass){
        RequestParams params=new RequestParams();
        params.put("client_name",fullname);
        params.put("client_email",eml);
        params.put("client_mob",contct);
        params.put("client_password",pass);
        params.put("confirm_password",confirmpass);
        params.put("client_address",add);
        params.put("client_city",cty);
        params.put("client_state",stt);
        params.put("client_zip",pincode);
        AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                showDialog();
            }


            @Override
            public void onFinish() {
                super.onFinish();
                hideDialog();
            }
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                Log.i("Register Response",s);
                try {
                    JSONObject jObj = new JSONObject(s);
                    int status = jObj.getInt("status");
                    if(status==1){
                        JSONObject details=jObj.getJSONObject("details");
                        String name=details.getString("client_name");
                        String email=details.getString("client_email");
                        String mob=details.getString("client_mob");
                        String pass=details.getString("client_password");
                      //  String cpass=details.getString("confirm_password");
                        String add=details.getString("client_address");
                        String city=details.getString("client_city");
                        String state=details.getString("client_state");
                        String zip=details.getString("client_zip");
                        String otp=details.getString("r_confirm");
                        Toast.makeText(getApplicationContext(), "User successfully registered.Enter OTP You Received!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                Registration_Activity.this,
                                OtpActivity.class);
                            intent.putExtra("otpis",otp+"");
                        startActivity(intent);
                        finish();

                    }
                    else{
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
                Toast.makeText(Registration_Activity.this, "Server Error...", Toast.LENGTH_SHORT).show();


            }
        };
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.post("http://khedutstore.com/garglibrary/ws/register.php",params,handler);
        Log.e("URL", AsyncHttpClient.getUrlWithQueryString(true,"http://khedutstore.com/garglibrary/ws/register.php",params));
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.setMessage("Loading...");
        pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
