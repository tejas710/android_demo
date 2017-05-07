package com.example.jay3165.garglibrary;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Otpfor_forgetpass extends AppCompatActivity {
    EditText etotp;
    Button button;
    String s,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_forgetpass);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        etotp=(EditText)findViewById(R.id.editotp);
        button=(Button)findViewById(R.id.btnotp);

        s=getIntent().getStringExtra("otp");
        p=getIntent().getStringExtra("mobileoremail");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp=etotp.getText().toString().trim();
                if(otp.equals(s)){
                    Intent intent=new Intent(Otpfor_forgetpass.this,new_confirm_password.class);
                    intent.putExtra("mobile",p);
                    startActivity(intent);
                    finish();
                }
                else {
                    etotp.setError("Otp does not match");
                }
            }
        });



    }
}
