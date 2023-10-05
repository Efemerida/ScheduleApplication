package com.example.scheduleapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapplication.R;
import com.example.scheduleapplication.entites.Day;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

public class ListAdapterTwo extends ArrayAdapter<Day> {

    private LayoutInflater inflater;
    private int layout;
    private List<Day> days;


    public ListAdapterTwo(Context context, int resource, List<Day> days) {
        super(context, resource, days);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.days = days;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);
        TextView nameOfDay = view.findViewById(R.id.nameOfDay);
        TextView dateOfDay = view.findViewById(R.id.dateOfDay);

        Day day = days.get(position);

        nameOfDay.setText(day.getNameDay());
        dateOfDay.setText(day.getDate());
        return view;
    }
}
