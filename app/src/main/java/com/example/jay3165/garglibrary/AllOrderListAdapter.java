package com.example.jay3165.garglibrary;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by jay3165 on 4/19/2017.
 */

public class AllOrderListAdapter extends RecyclerView.Adapter<AllOrderListAdapter.orderlistViewHolder>  {


    public ArrayList<orderlist> orderlistArrayList;
    public Context context;
    AllOrderListAdapter.Clickable clickable;

    public AllOrderListAdapter(ArrayList<orderlist> orderlistArrayList, Context context, AllOrderListAdapter.Clickable clickable) {
        this.orderlistArrayList = orderlistArrayList;
        this.context = context;
        this.clickable = clickable;
    }
    public interface Clickable {
        void click(int position);
    }

    public class orderlistViewHolder extends RecyclerView.ViewHolder{
        TextView txtstatus,txtorderno,txtpayment;
        CardView cardView;

        public orderlistViewHolder(View view){
            super(view);
            txtstatus=(TextView)view.findViewById(R.id.txtstatus1);
            txtorderno=(TextView)view.findViewById(R.id.txtorderno1);
            txtpayment=(TextView)view.findViewById(R.id.txtpayableamount1);



            cardView=(CardView)view.findViewById(R.id.cdview2);
        }
    }

    @Override
    public AllOrderListAdapter.orderlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_myorder, parent, false);
        return new AllOrderListAdapter.orderlistViewHolder(view);
    }
    @Override
    public void onBindViewHolder(AllOrderListAdapter.orderlistViewHolder holder, final int position) {

        orderlist orderlist=orderlistArrayList.get(position);

       if(orderlist.getConfirm_status().equals("1")){
           holder.txtstatus.setText("Dispatch");

       }else if (orderlist.getConfirm_status().equals("2")){
           holder.txtstatus.setText("transit");
       }else if (orderlist.getConfirm_status().equals("3")){
           holder.txtstatus.setText("Delivered");
       }
        holder.txtorderno.setText(orderlist.getOrder_no());
        holder.txtpayment.setText("₹"+orderlist.getCart_total());



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickable.click(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return orderlistArrayList.size();
    }
}
