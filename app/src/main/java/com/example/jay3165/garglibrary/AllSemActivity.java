package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AllSemActivity extends AppCompatActivity implements AllSemAdapter.Clickable{
    private RecyclerView rvSem;
    private RecyclerView.LayoutManager semActivityLayout;
    ArrayList<sem> SemArrayList = new ArrayList<>();
    AllSemAdapter.Clickable clickable=this;
    Toolbar toolbar;
    TextView tvnodata;
    Button b;
    ImageView back;
    String s=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sem);
        tvnodata = (TextView)findViewById(R.id.tvnodata);
        back=(ImageView)findViewById(R.id.imgback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(AllSemActivity.this,AllCourseActivity.class);
                startActivity(back);
                finish();
            }
        });

        rvSem=(RecyclerView)findViewById(R.id.rvSem);
        semActivityLayout=new GridLayoutManager(this,2);
        tvnodata.setVisibility(View.GONE);

        rvSem.setLayoutManager(semActivityLayout);
         s = getIntent().getStringExtra("maincategory_id");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SemArrayList.size() == 0) {
            SemData();

        }

    }
    public void SemData() {
        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.addHeader("Accept", "application/json");
        mClient.addHeader("Content-Type", "application/json");
        mClient.get(this, "http://khedutstore.com/garglibrary/ws/main-subcat.php?cat_id="+s, new JsonHttpResponseHandler() {
                    ProgressDialog mProgressDialog;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONArray mJsonArray = response.getJSONArray("details");
                            JSONObject mJsonObject;
                            for (int i = 0; i < mJsonArray.length(); i++) {
                                mJsonObject = mJsonArray.getJSONObject(i);
                                SemArrayList.add(new sem(mJsonObject.getString("main_sub_id"), mJsonObject.getString("main_sub_name"), mJsonObject.getString("cat_id")));
                                Log.e("Array Length", SemArrayList.size() + "");

                                rvSem.setAdapter(new AllSemAdapter(SemArrayList, getApplicationContext(), clickable));


                            }

                        } catch (JSONException e) {
                            tvnodata.setVisibility(View.VISIBLE);
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
                        mProgressDialog = ProgressDialog.show(AllSemActivity.this, "Loading", "Please Wait", true, false);
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
    public void click(int position) {
        Intent i=new Intent(AllSemActivity.this,AllSubjectActivity.class);
        i.putExtra("mainsub_id",SemArrayList.get(position).getMain_sub_id());
        i.putExtra("catid",SemArrayList.get(position).getCat_id());
        i.putExtra("maincategory_id",s);


        startActivity(i);
        Toast.makeText(this,SemArrayList.get(position).getMain_sub_name(),Toast.LENGTH_SHORT).show();
    }
}