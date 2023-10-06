package com.example.scheduleapplication.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapplication.MainActivity;
import com.example.scheduleapplication.R;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.fragments.PageFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.val;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.DayListHolder> {

    public interface OnStateClickListener{
        void onStateClick(int position);
    }

    private LayoutInflater inflater;
    private List<Day> days;

    public int currentPosition = 0;

    private DayAdapterFragment dayAdapterFragment;

    private final OnStateClickListener onStateClickListener;
    Drawable active;
    Drawable passive;
    Context context;



    public DayListAdapter(Context context, List<Day> days, DayAdapterFragment dayAdapterFragment, OnStateClickListener onStateClickListener, Drawable active, Drawable passive) {
        this.inflater = LayoutInflater.from(context);
        this.days = days;
        this.dayAdapterFragment = dayAdapterFragment;
        this.onStateClickListener = onStateClickListener;
        this.active = active;
        this.passive = passive;
        this.context = context;
    }




    @NonNull
    @Override
    public DayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_fragment, parent, false);
        return new DayListAdapter.DayListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayListHolder holder, @SuppressLint("RecyclerView") int position, @NonNull List<Object> payloads) {
        if(!payloads.isEmpty()) {


            int code = (int)payloads.get(0);

            int positionActive = MainActivity.positionCurrent;
            int current = MainActivity.currentDay;


            if(code == 1){
                Log.d("taggg", "code 1 and pos " + position);
                GradientDrawable gradientDrawable = (GradientDrawable) holder.itemView.getBackground();
                gradientDrawable.setColor(ContextCompat.getColor(context, R.color.bg_selected));

            }
            else if(code == 3){
                GradientDrawable gradientDrawable = (GradientDrawable) holder.itemView.getBackground();
                gradientDrawable.setColor(ContextCompat.getColor(context, R.color.bg_current));
            }
            else if((positionActive!=position || code == 0) && (position!=current)){
                Log.d("taggg", "code 0 and pos " + position);
                GradientDrawable gradientDrawable = (GradientDrawable) holder.itemView.getBackground();
                gradientDrawable.setColor(ContextCompat.getColor(context, R.color.bg_standart));

            }

        }
        else{
        super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DayListHolder holder, int position) {

        int positionActive = MainActivity.positionCurrent;
        int currentDay = MainActivity.currentDay;



        if(positionActive != position){
            GradientDrawable gradientDrawable = (GradientDrawable) holder.itemView.getBackground();
            gradientDrawable.setColor(ContextCompat.getColor(context, R.color.bg_standart));
            Log.d("taggg", "list " + position + " " + currentDay);
            if(position==currentDay){
                gradientDrawable.setColor(ContextCompat.getColor(context, R.color.bg_current));
            }


        }

        if(positionActive==position){
            GradientDrawable gradientDrawable = (GradientDrawable) holder.itemView.getBackground();
            gradientDrawable.setColor(ContextCompat.getColor(context, R.color.bg_selected));
        }


        Day day = days.get(position);
            holder.name.setText(day.getNameDay());
            holder.date.setText(day.getDate());
            int s = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStateClickListener.onStateClick(s);
                }
            });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class DayListHolder extends RecyclerView.ViewHolder {

        final TextView name, date;


        public DayListHolder(@NonNull View view){
            super(view);

            name = view.findViewById(R.id.nameOfDay);
            date = view.findViewById(R.id.dateOfDay);
        }
    }
}
