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
 * Created by jay3165 on 4/20/2017.
 */

public class AllMyOrderDescriptionAdapter extends RecyclerView.Adapter<AllMyOrderDescriptionAdapter.orderdesViewHolder> {
    public ArrayList<orderdata> orderdesArrayList;
    public Context context;



    public AllMyOrderDescriptionAdapter(ArrayList<orderdata> orderdesArrayList, Context context) {
        this.orderdesArrayList = orderdesArrayList;
        this.context = context;

    }

    public class orderdesViewHolder extends RecyclerView.ViewHolder{
        TextView tvproductname,tvprice,tvqty;

        CardView cardView;

        public orderdesViewHolder(View view){
            super(view);
            tvproductname=(TextView)view.findViewById(R.id.txtproductname);


            tvqty=(TextView)view.findViewById(R.id.txtqty);
            tvprice=(TextView)view.findViewById(R.id.txtprice);




            cardView=(CardView)view.findViewById(R.id.cdview2);
        }
    }
    @Override
    public AllMyOrderDescriptionAdapter.orderdesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_myorderlist, parent, false);
        return new AllMyOrderDescriptionAdapter.orderdesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(AllMyOrderDescriptionAdapter.orderdesViewHolder holder, final int position) {

        orderdata orderdata=orderdesArrayList.get(position);
        holder.tvproductname.setText(orderdata.getP_name());


        holder.tvqty.setText(orderdata.getQuantity()+"x"+orderdata.getPrice());

            int total = Integer.parseInt(orderdata.getQuantity()) * Integer.parseInt(orderdata.getPrice());


            holder.tvprice.setText("₹" + total);





    }
    @Override
    public int getItemCount() {
        return orderdesArrayList.size();
    }
}

