package com.example.scheduleapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapplication.R;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.fragments.PageFragment;

import java.util.List;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.DayListHolder> {

    public interface OnStateClickListener{
        void onStateClick(int position);
    }

    private LayoutInflater inflater;
    private List<Day> days;

    private DayAdapterFragment dayAdapterFragment;

    private final OnStateClickListener onStateClickListener;


    public DayListAdapter(Context context, List<Day> days, DayAdapterFragment dayAdapterFragment, OnStateClickListener onStateClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.days = days;
        this.dayAdapterFragment = dayAdapterFragment;
        this.onStateClickListener = onStateClickListener;
    }




    @NonNull
    @Override
    public DayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_fragment, parent, false);
        return new DayListAdapter.DayListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayListHolder holder, int position) {
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
