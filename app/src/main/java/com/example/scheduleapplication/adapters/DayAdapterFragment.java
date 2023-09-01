package com.example.scheduleapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.fragments.PageFragment;
import com.example.scheduleapplication.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;

public class DayAdapterFragment  extends FragmentStateAdapter {

    List<Day> days = new ArrayList<>();

    public DayAdapterFragment(FragmentActivity fragmentActivity){
        super(fragmentActivity);
        Runnable runnable = () -> ScheduleService.getContent(days);
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
