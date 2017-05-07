package com.example.jay3165.garglibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by jay3165 on 3/5/2017.
 */

public class AllSubjectAdapter extends RecyclerView.Adapter<AllSubjectAdapter.subjectViewHolder> {
    public ArrayList<subject> subjectArrayList;
    public Context context;
    Clickable clickable;

    public AllSubjectAdapter(ArrayList<subject> subjectArrayList, Context context, Clickable clickable) {
        this.subjectArrayList = subjectArrayList;
        this.context = context;
        this.clickable = clickable;
    }
    public interface Clickable {
        void click(int position);
    }

    public class subjectViewHolder extends RecyclerView.ViewHolder{
        Button btnsubname;
        public subjectViewHolder(View view){
            super(view);
            btnsubname=(Button)view.findViewById(R.id.btnsubject);
        }

    }
    @Override
    public AllSubjectAdapter.subjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_subject_item, parent, false);
        return new AllSubjectAdapter.subjectViewHolder(view);
    }
    @Override
    public void onBindViewHolder(AllSubjectAdapter.subjectViewHolder holder, final int position) {
        subject subject = subjectArrayList.get(position);
        holder.btnsubname.setText(subject.getSub_cat_name());

        holder.btnsubname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickable.click(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }
}


