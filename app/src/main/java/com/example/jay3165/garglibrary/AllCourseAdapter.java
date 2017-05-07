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
 * Created by jay3165 on 3/4/2017.
 */

public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.courseViewHolder> {

        public ArrayList<course> courseArrayList;
        public Context context;
        Clickable clickable;

        public AllCourseAdapter(ArrayList<course> courseArrayList, Context context, Clickable clickable) {
            this.courseArrayList = courseArrayList;
            this.context = context;
            this.clickable = clickable;
        }

        public interface Clickable {
            void click(int position);
        }


        public class courseViewHolder extends RecyclerView.ViewHolder {
            TextView tvcourseName;
            CardView cardView;

            public courseViewHolder(View view) {
                super(view);
                tvcourseName = (TextView) view.findViewById(R.id.tvCourse);
                cardView = (CardView) view.findViewById(R.id.cdview);

            }

        }


        @Override
        public AllCourseAdapter.courseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_course_item, parent, false);
            return new AllCourseAdapter.courseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AllCourseAdapter.courseViewHolder holder, final int position) {
            course course = courseArrayList.get(position);
            holder.tvcourseName.setText(course.getMain_cat_name());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickable.click(position);
                }
            });
        }


        @Override
        public int getItemCount() {
            return courseArrayList.size();
        }
    }


