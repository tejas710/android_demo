package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AllSubjectActivity extends AppCompatActivity implements AllSubjectAdapter.Clickable {
    private RecyclerView rvSub;
    private RecyclerView.LayoutManager subjectActivityLayout;
    ArrayList<subject> subjectArrayList = new ArrayList<>();
    AllSubjectAdapter.Clickable clickable = this;
    String s=null;
    String c,b=null;
    ImageView back;
    TextView tvnodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subject);
        tvnodata = (TextView)findViewById(R.id.tvnodata);
        tvnodata.setVisibility(View.GONE);
        back=(ImageView)findViewById(R.id.imgback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AllSubjectActivity.this,AllSemActivity.class);
                i.putExtra("maincategory_id",b);
                startActivity(i);
                finish();
            }
        });

        rvSub=(RecyclerView)findViewById(R.id.rvSub);
        subjectActivityLayout=new LinearLayoutManager(this);
        rvSub.setLayoutManager(subjectActivityLayout);
        s = getIntent().getStringExtra("mainsub_id");
        c=getIntent().getStringExtra("catid");
        b=getIntent().getStringExtra("maincategory_id");



    }
    @Override
    protected void onStart() {
        super.onStart();
        if (subjectArrayList.size() == 0) {
            SubjectData();
        }
    }

    public void SubjectData(){
        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.addHeader("Accept", "application/json");
        mClient.addHeader("Content-Type", "application/json");
        mClient.get(this,"http://khedutstore.com/garglibrary/ws/main-sub_subcat.php?sub_cat_id="+s,new JsonHttpResponseHandler(){
            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    JSONArray mJsonArray = response.getJSONArray("details");
                    JSONObject mJsonObject;
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        subjectArrayList.add(new subject(mJsonObject.getString("cat_id"), mJsonObject.getString("sub_cat_id"), mJsonObject.getString("sub_cat_name"),mJsonObject.getString("main_cat_id")));
                        Log.e("Array Length", subjectArrayList.size() + "");
                        rvSub.setAdapter(new AllSubjectAdapter(subjectArrayList, getApplicationContext(), clickable));

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
                mProgressDialog = ProgressDialog.show(AllSubjectActivity.this, "Loading", "Please Wait", true, false);
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
        Intent i=new Intent(AllSubjectActivity.this,AllBookActivity.class);
        i.putExtra("maincatid",s);
        i.putExtra("catid",b);
        i.putExtra("subcatid",subjectArrayList.get(position).getSub_cat_id());

        startActivity(i);
        Toast.makeText(this,subjectArrayList.get(position).getSub_cat_name(),Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.activity_all_sem:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}

