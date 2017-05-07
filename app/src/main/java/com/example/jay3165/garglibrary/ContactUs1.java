package com.example.jay3165.garglibrary;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUs1 extends AppCompatActivity {
    TextView t1,t2,t3,t4;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us1);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));


        t1 = (TextView) findViewById(R.id.jay);
        t2 = (TextView) findViewById(R.id.shreya);
        t3 = (TextView) findViewById(R.id.jay2);
        t4 = (TextView) findViewById(R.id.shreya2);

        img=(ImageView)findViewById(R.id.imgviewgps);
        String udata = t1.getText() + "";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        t1.setText(content);
        String udata1 = t2.getText() + "";
        SpannableString content1 = new SpannableString(udata1);
        content1.setSpan(new UnderlineSpan(), 0, udata1.length(), 0);
        t2.setText(content1);
        final String jay1 = t1.getText() + "";
        t1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + jay1));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
        final String shreya1 = t2.getText() + "";
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + shreya1));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.co.in/maps/place/Garg+Library/@23.0283381,72.571156,16z/data=!4m5!3m4!1s0x0:0xc2b8f0bf58a5c06b!8m2!3d23.0283184!4d72.571156";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                startActivity(intent);
            }
        });


        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Send email", "");
                String[] TO = {"garglibrary123@gmail.com"};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactUs1.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }

        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Send email", "");
                String[] TO = {"garglibrary.book@gmail.com"};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactUs1.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

}

