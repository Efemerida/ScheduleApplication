package com.example.scheduleapplication.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.fragments.PageFragment;
import com.example.scheduleapplication.service.DataService;
import com.example.scheduleapplication.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;

public class DayAdapterFragment  extends FragmentStateAdapter {

    List<Day> days = new ArrayList<>();

    public DayAdapterFragment(FragmentActivity fragmentActivity, Context context){
        super(fragmentActivity);
        Runnable runnable = () -> {
            DataService dataService = DataService.initial(context);

            if(!dataService.isActual()){
                dataService.loadData();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new PageFragment(position);
    }

    @Override
    public int getItemCount() {
        return 12;
    }
}
