package com.example.jay3165.garglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static android.R.attr.theme;
import static android.R.attr.thumb;

public class Myorder extends AppCompatActivity implements AllOrderListAdapter.Clickable {
    private RecyclerView rvBook;
    private RecyclerView.LayoutManager orderActivityLayout;
    ArrayList<orderlist> orderlistArrayList = new ArrayList<>();
    //ArrayList<orderdata> orderdataArrayList = new ArrayList<>();
    AllOrderListAdapter.Clickable clickable=this;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    StringEntity entity;
    TextView tvnodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
tvnodata = (TextView)findViewById(R.id.tvnodata);
        tvnodata.setVisibility(View.GONE);
        sharedPreferences = getSharedPreferences(Loginpref.MY_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rvBook=(RecyclerView)findViewById(R.id.rvBook);
        orderActivityLayout=new LinearLayoutManager(this);
        rvBook.setLayoutManager(orderActivityLayout);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (orderlistArrayList.size() == 0) {
            orderlistData();
        }
    }
    public void orderlistData() {
       /* JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("u_id",sharedPreferences.getString(Loginpref.ID,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            entity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        RequestParams params=new RequestParams();
        params.put("u_id",sharedPreferences.getString(Loginpref.ID,""));

        AsyncHttpClient mClient = new AsyncHttpClient();
     //   mClient.addHeader("Accept", "application/json");
     //   mClient.addHeader("Content-Type", "application/json");
        mClient.post(Myorder.this,"http://khedutstore.com/garglibrary/ws/order_list.php",params, new JsonHttpResponseHandler() {
            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {


                    JSONArray mJsonArray = response.getJSONArray("details");
                    JSONObject mJsonObject;
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        ArrayList<orderdata> orderdataArrayList = new ArrayList<>();
                        mJsonObject = mJsonArray.getJSONObject(i);
                        JSONArray mOrderData = mJsonObject.getJSONArray("order_data");
                        JSONObject mOrder;
                        for(int j= 0 ;j<mOrderData.length();j++)
                        {
                            mOrder = mOrderData.getJSONObject(j);
                            orderdataArrayList.add(new orderdata(mOrder.getString("p_id"),mOrder.getString("quantity"),mOrder.getString("price"),mOrder.getString("p_name")));
                        }
                        orderlistArrayList.add(new orderlist(mJsonObject.getString("order_id"), mJsonObject.getString("order_no"), mJsonObject.getString("bill_no"), mJsonObject.getString("cart_total")
                                , mJsonObject.getString("order_status"), mJsonObject.getString("confirm_status"),orderdataArrayList));

                        rvBook.setAdapter(new AllOrderListAdapter(orderlistArrayList, getApplicationContext(), clickable));

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
                mProgressDialog = ProgressDialog.show(Myorder.this, "Loading", "Please Wait", true, false);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }


            @Override
    public void click(int position) {
                orderlist orderlist=orderlistArrayList.get(position);

                Intent intent=new Intent(Myorder.this,AllMyOrderDescription.class);
                intent.putExtra("data",orderlist);




                startActivity(intent);



    }
}
