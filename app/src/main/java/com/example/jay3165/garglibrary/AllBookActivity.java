package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AllBookActivity extends AppCompatActivity implements AllBookAdapter.Clickable{
    private RecyclerView rvBook;
    private RecyclerView.LayoutManager bookActivityLayout;
    ArrayList<particularbook> BookArrayList = new ArrayList<>();
    AllBookAdapter.Clickable clickable=this;
    String s,c,a=null;
TextView tvnodata;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_book);
        tvnodata = (TextView)findViewById(R.id.tvnodata);
        tvnodata.setVisibility(View.GONE);
        back=(ImageView)findViewById(R.id.imgback1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(AllBookActivity.this,AllSubjectActivity.class);
                back.putExtra("maincatid",s);
                back.putExtra("catid",c);
                startActivity(back);
                finish();

            }
        });
        rvBook=(RecyclerView)findViewById(R.id.rvBook);
        bookActivityLayout=new LinearLayoutManager(this);
        rvBook.setLayoutManager(bookActivityLayout);
        s = getIntent().getStringExtra("maincatid");
        c=getIntent().getStringExtra("catid");
        a=getIntent().getStringExtra("subcatid");



    }
    @Override
    protected void onStart() {
        super.onStart();
        if (BookArrayList.size() == 0) {
            BookData();
        }
    }
    public void BookData() {
        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.addHeader("Accept", "application/json");
        mClient.addHeader("Content-Type", "application/json");
        mClient.get(this, "http://khedutstore.com/garglibrary/ws/products.php?cat_id="+c+"&sub_cat_id="+s+"&sub_sub_cat_id="+a, new JsonHttpResponseHandler() {
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



                                BookArrayList.add(new particularbook(mJsonObject.getString("p_id"), mJsonObject.getString("p_name"),
                                        mJsonObject.getString("p_price"),mJsonObject.getString("p_brand_name"),
                                        mJsonObject.getString("main_cat_id"),mJsonObject.getString("main_sub_id"),
                                        mJsonObject.getString("sub_cat_id"),mJsonObject.getString("p_specification"),
                                        thumb+mJsonObject.getString("p_image"),mJsonObject.getString("p_discount"),
                                        mJsonObject.getString("p_cat_id"),mJsonObject.getString("p_status"),
                                        mJsonObject.getString("p_stock"),newprice+"",discount+""));
                                Log.e("Array Length", BookArrayList.size() + "");
                                rvBook.setAdapter(new AllBookAdapter(BookArrayList, getApplicationContext(), clickable));

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
                        mProgressDialog = ProgressDialog.show(AllBookActivity.this, "Loading", "Please Wait", true, false);
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
       Intent i=new Intent(AllBookActivity.this,AllBookdetailsActivity.class);
        i.putExtra("p_id",BookArrayList.get(position).getP_id());

        startActivity(i);

        Toast.makeText(this,BookArrayList.get(position).getP_name(),Toast.LENGTH_SHORT).show();
    }
}

