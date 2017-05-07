package com.example.jay3165.garglibrary;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jay3165 on 3/5/2017.
 */

public class AllSemAdapter extends RecyclerView.Adapter<AllSemAdapter.semViewHolder> {

    public ArrayList<sem> semArrayList;
    public Context context;
    Clickable clickable;

    public AllSemAdapter(ArrayList<sem> semArrayList, Context context, Clickable clickable) {
        this.semArrayList = semArrayList;
        this.context = context;
        this.clickable = clickable;
    }
    public interface Clickable {
        void click(int position);
    }
    public class semViewHolder extends RecyclerView.ViewHolder{
        TextView tvsemname;
        CardView cardView;

        public semViewHolder(View view)
        {
            super(view);
            tvsemname=(TextView)view.findViewById(R.id.tvSem);
            cardView=(CardView)view.findViewById(R.id.cdview1);

        }
    }
    @Override
    public AllSemAdapter.semViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sem_item, parent, false);
        return new AllSemAdapter.semViewHolder(view);
    }
    @Override
    public void onBindViewHolder(AllSemAdapter.semViewHolder holder, final int position) {
        sem sem = semArrayList.get(position);
        holder.tvsemname.setText(sem.getMain_sub_name());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickable.click(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return semArrayList.size();
    }

}
