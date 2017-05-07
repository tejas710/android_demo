package com.example.jay3165.garglibrary;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by jay3165 on 3/6/2017.
 */

public class AllBookdetailsAdapter extends RecyclerView.Adapter<AllBookdetailsAdapter.bookdetailsViewHolder> {
    public ArrayList<bookdetails> bookdetailsArrayList;
    public Context context;
    Clickable clickable;



    public AllBookdetailsAdapter(ArrayList<bookdetails> bookdetailsArrayList, Context context, Clickable clickable) {
        this.bookdetailsArrayList = bookdetailsArrayList;
        this.context = context;
        this.clickable = clickable;
    }
    public interface Clickable {
        void click(int position);
    }
    public class bookdetailsViewHolder extends RecyclerView.ViewHolder{
        TextView tvbookname,tvspecification,tvprice,tvaddcart;

        ImageView img1;
        private Context mContext;
        private Activity mActivity;

        private RelativeLayout mRelativeLayout;
        private Button mButton;

        private PopupWindow mPopupWindow;



        public bookdetailsViewHolder(View view){
            super(view);
            tvbookname=(TextView)view.findViewById(R.id.text1_bookdetails);
            tvspecification=(TextView)view.findViewById(R.id.text2_bookdetails);
            tvprice=(TextView)view.findViewById(R.id.txt_showprice);
            tvaddcart=(TextView)view.findViewById(R.id.txt_addtocart);
            img1=(ImageView) view.findViewById(R.id.imageView_bookimg);



        }
    }
    @Override
    public AllBookdetailsAdapter.bookdetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bookdetails_item, parent, false);
        return new AllBookdetailsAdapter.bookdetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllBookdetailsAdapter.bookdetailsViewHolder holder, final int position) {
        bookdetails bookdetails = bookdetailsArrayList.get(position);
        holder.tvbookname.setText(bookdetails.getP_name());
        holder.tvspecification.setText(bookdetails.getP_specification());
        holder.tvprice.setText("₹"+bookdetails.getP_newprice());

        Glide.with(context).load(bookdetails.getP_image()).into(holder.img1);
        holder.tvaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickable.click(position);



            }
        });
    }
    @Override
    public int getItemCount() {
        return bookdetailsArrayList.size();
    }
}

