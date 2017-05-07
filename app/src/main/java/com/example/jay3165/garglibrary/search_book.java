package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static android.R.attr.thumb;

public class search_book extends AppCompatActivity implements AllBookAdapter.Clickable {
    ArrayList<particularbook> mArrayList;
   private RecyclerView mRecyclerView;
    String name=null;
    private RecyclerView.LayoutManager booksearchActivityLayout;
    AllBookAdapter.Clickable clickable = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        mArrayList = new ArrayList<particularbook>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rvBookSearch);
        booksearchActivityLayout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(booksearchActivityLayout);
        Intent it = getIntent();
        getSupportActionBar().setTitle(it.getStringExtra("s"));
         name = it.getStringExtra("s");

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mArrayList.size() == 0) {
            BookData();
        }
    }
    public void BookData(){
        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.addHeader("Accept", "application/json");
        mClient.addHeader("Content-Type", "application/json");
        mClient.get(this, "http://khedutstore.com/garglibrary/ws/search.php?keyword="+name, new JsonHttpResponseHandler() {
                    ProgressDialog mProgressDialog;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String thumb =response.getString("thmbimg_url");

                            JSONArray mJsonArray = response.getJSONArray("details");
                            JSONObject mJsonObject;
                            for (int i = 0; i < mJsonArray.length(); i++) {
                                mJsonObject = mJsonArray.getJSONObject(i);
                                int discount=(Integer.parseInt(mJsonObject.getString("p_price")) * Integer.parseInt(mJsonObject.getString("p_discount")))/100;
                                int newprice=Integer.parseInt(mJsonObject.getString("p_price")) - discount;
                                Log.i("jjjjjjjj",mJsonObject.getString("p_price"));

                                Log.i("lllllll",mJsonObject.getString("p_discount"));
                                Log.i("discount",discount+"");
                                mArrayList.add(new particularbook(mJsonObject.getString("p_id"), mJsonObject.getString("p_name"), mJsonObject.getString("p_price"),mJsonObject.getString("p_brand_name"),mJsonObject.getString("main_cat_id"),mJsonObject.getString("main_sub_id"),mJsonObject.getString("sub_cat_id"),mJsonObject.getString("p_specification"),thumb+mJsonObject.getString("p_image"),mJsonObject.getString("p_discount"),mJsonObject.getString("p_cat_id"),mJsonObject.getString("p_status"),mJsonObject.getString("p_stock"),newprice+"",discount+""));
                                Log.e("Array Length",mArrayList.size() + "");
                                mRecyclerView.setAdapter(new AllBookAdapter(mArrayList, getApplicationContext(), clickable));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(search_book.this, "No Data Connection!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mProgressDialog = ProgressDialog.show(search_book.this, "Loading", "Please Wait", true, false);
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
        Intent i=new Intent(search_book.this,AllBookdetailsActivity.class);
        i.putExtra("p_id",mArrayList.get(position).getP_id());
        startActivity(i);

        Toast.makeText(this,mArrayList.get(position).getP_name(),Toast.LENGTH_SHORT).show();

    }
}