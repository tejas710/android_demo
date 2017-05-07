package com.example.jay3165.garglibrary;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by jay3165 on 3/7/2017.
 */

public class AllCartAdapter extends RecyclerView.Adapter<AllCartAdapter.cartViewHolder> {
    public ArrayList<BookAddToCart> cartArrayList;
    public Context context;
    Clickable clickable;








    public AllCartAdapter(ArrayList<BookAddToCart> cartArrayList, Context context, Clickable clickable) {
        this.cartArrayList = cartArrayList;
        this.context = context;
        this.clickable = clickable;
    }
    public interface Clickable {
        void click(int position);
        void ivClick(int position);
    }
    public class cartViewHolder extends RecyclerView.ViewHolder{
        TextView tvbookname,tvqty,tvprice;
        ImageView img;
        Button b;
        CardView cardView;

        public cartViewHolder(View view){
            super(view);
            tvbookname=(TextView)view.findViewById(R.id.tv_bookname1);



            tvprice=(TextView)view.findViewById(R.id.tv_originalprice1);
            tvqty=(TextView)view.findViewById(R.id.qty);

           img=(ImageView)view.findViewById(R.id.bookimg1);
            cardView=(CardView)view.findViewById(R.id.cdviewcart);
             b=(Button)view.findViewById(R.id.btn_delete);



        }

    }
    @Override
    public AllCartAdapter.cartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item, parent, false);
        return new AllCartAdapter.cartViewHolder(view);
    }
    @Override
    public void onBindViewHolder(AllCartAdapter.cartViewHolder holder, final int position) {

        BookAddToCart cart= cartArrayList.get(position);
        holder.tvbookname.setText(cart.getP_name());

        holder.tvprice.setText("₹"+cart.getP_total());
        holder.tvqty.setText(cart.getP_qty()+"x"+cart.getP_price());

        Glide.with(context).load(cart.getP_image()).into(holder.img);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickable.ivClick(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }
}
