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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jay3165 on 3/5/2017.
 */

public class AllBookAdapter extends RecyclerView.Adapter<AllBookAdapter.bookViewHolder> {
    public ArrayList<particularbook> particularbookArrayList;
    public Context context;
    Clickable clickable;

    public AllBookAdapter(ArrayList<particularbook> particularbookArrayList, Context context, Clickable clickable) {
        this.particularbookArrayList = particularbookArrayList;
        this.context = context;
        this.clickable = clickable;
    }
    public interface Clickable {
        void click(int position);
    }
    public class bookViewHolder extends RecyclerView.ViewHolder{
        TextView tvbookname,tvpublicationname,tvprice,tvdiscountprice,tvmainprice,tvsave;
        ImageView img;
        CardView cardView;

        public bookViewHolder(View view){
            super(view);
            tvbookname=(TextView)view.findViewById(R.id.tv_custome_bookname);
            tvpublicationname=(TextView)view.findViewById(R.id.tv_custome_publication);
            tvprice=(TextView)view.findViewById(R.id.tv_cutome_originalprice);
            tvprice.setPaintFlags(tvprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvdiscountprice=(TextView)view.findViewById(R.id.tvRs);
            tvmainprice=(TextView)view.findViewById(R.id.tvorprice);
            tvsave=(TextView)view.findViewById(R.id.tvsave);

            img=(ImageView)view.findViewById(R.id.bookimg);


            cardView=(CardView)view.findViewById(R.id.cdview2);
        }
    }
    @Override
    public AllBookAdapter.bookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_book_item, parent, false);
        return new AllBookAdapter.bookViewHolder(view);
    }
    @Override
    public void onBindViewHolder(AllBookAdapter.bookViewHolder holder, final int position) {
        particularbook particularbook = particularbookArrayList.get(position);

        holder.tvbookname.setText(particularbook.getP_name());
        holder.tvpublicationname.setText(particularbook.getP_brand_name());
        holder.tvprice.setText("₹"+particularbook.getP_price());
        holder.tvdiscountprice.setText(particularbook.getP_discount()+"% Discount");

       holder.tvmainprice.setText("₹"+particularbook.getP_newprice());
        holder.tvsave.setText("(you save"+particularbook.getP_save()+"₹)");
        Log.i("image",particularbook.getP_image());
        Glide.with(context).load(particularbook.getP_image()).into(holder.img);

       holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickable.click(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return particularbookArrayList.size();
    }
}




