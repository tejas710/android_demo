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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AllMyOrderDescription extends AppCompatActivity {
    private RecyclerView rvOrder;
    private RecyclerView.LayoutManager orderActivityLayout;


    String s;
    String p;
    String w;
    String or;
    orderlist orderlist1;
        ArrayList<orderdata> orderdataArrayList=new ArrayList<>();
int t;
    TextView txtstatus,txttotal,txtorderno,txtpayamnt,txtqty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_my_order_description);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        txtqty=(TextView)findViewById(R.id.txtqty1);
        txtstatus=(TextView)findViewById(R.id.txtcancle);
        txttotal=(TextView)findViewById(R.id.txttotal);
        txtorderno=(TextView)findViewById(R.id.txtorderno);
        txtpayamnt=(TextView)findViewById(R.id.txtamnt1);
        rvOrder=(RecyclerView)findViewById(R.id.rvorder);
        orderActivityLayout=new LinearLayoutManager(this);
        rvOrder.setLayoutManager(orderActivityLayout);
        Intent intent = getIntent();
        orderlist1=intent.getParcelableExtra("data");
        orderdataArrayList=orderlist1.getOrderdata();
        rvOrder.setAdapter(new AllMyOrderDescriptionAdapter(orderdataArrayList, getApplicationContext()));
        txtstatus.setText(":"+orderlist1.getConfirm_status());
        txtorderno.setText(":"+orderlist1.getOrder_no());
        txtpayamnt.setText(":₹"+orderlist1.getCart_total());
        txttotal.setText(":₹"+orderlist1.getCart_total());
        for (int i=0;i<orderdataArrayList.size();++i){
            t+=Integer.parseInt(orderdataArrayList.get(i).getQuantity().toString());
        }
        txtqty.setText(t+"  PRODUCT");




    }

}


