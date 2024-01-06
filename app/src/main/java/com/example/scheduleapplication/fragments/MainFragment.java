package com.example.scheduleapplication.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.scheduleapplication.MainActivity;
import com.example.scheduleapplication.R;
import com.example.scheduleapplication.adapters.DayAdapterFragment;
import com.example.scheduleapplication.adapters.DayListAdapter;
import com.example.scheduleapplication.adapters.ListAdapterTwo;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.service.DayService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    ViewPager2 viewPager2;
    static RecyclerView recyclerView;

    ListView listView;
    public Context context = getContext();

    public MainActivity mainActivity = MainActivity.mainActivity;

    public static int positionCurrent = 0;
    public static int currentDay = 0;

    public MainFragment() {
        super(R.layout.activity_main);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }

        int position = date.getDayOfWeek().getValue()-1;
        if(date.getDayOfWeek().getValue()==7) position=0;
        positionCurrent = position;
        currentDay = position;


        viewPager2 = view.findViewById(R.id.viewPager);
        recyclerView = view.findViewById(R.id.day_list);

        List<Day> days = new ArrayList<>();

        mainActivity = MainActivity.mainActivity;
        DayAdapterFragment dayAdapterFragment = new DayAdapterFragment(mainActivity, mainActivity);
        DayService dayService = new DayService();
        dayService.setDays(days);

        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.s_select_radius_border);
        Drawable drawablePassive = getResources().getDrawable(R.drawable.select_radius_border);

        DayListAdapter dayListAdapter;


        DayListAdapter.OnStateClickListener onStateClickListener = position1 -> {
            viewPager2.setCurrentItem(position1);
            if(currentDay == positionCurrent  && position1 !=currentDay){
                recyclerView.getAdapter().notifyItemChanged(positionCurrent, 3);
            }
            recyclerView.getAdapter().notifyItemChanged(position1, 1);
            recyclerView.getAdapter().notifyItemChanged(positionCurrent, 0);


            positionCurrent = position1;



        };
        dayListAdapter = new DayListAdapter(mainActivity, days, dayAdapterFragment, onStateClickListener, drawable, drawablePassive);
        recyclerView.setAdapter(dayListAdapter);
        viewPager2.setAdapter(dayAdapterFragment);
        viewPager2.setCurrentItem(positionCurrent);
        recyclerView.getAdapter().notifyItemChanged(currentDay, 3);
        recyclerView.offsetChildrenHorizontal(14);


        mainActivity.getSupportActionBar().hide();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if(currentDay == positionCurrent && position!=currentDay){
                    recyclerView.getAdapter().notifyItemChanged(positionCurrent, 3);
                }

                recyclerView.getAdapter().notifyItemChanged(position, 1);
                recyclerView.getAdapter().notifyItemChanged(positionCurrent, 0);

                LocalDate localDate = LocalDate.now();
                DayOfWeek day = localDate.getDayOfWeek();

                if(position>=6)
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                if(position<=5)
                    recyclerView.smoothScrollToPosition(0);

                positionCurrent = position;
                super.onPageSelected(position);
            }
        });
        super.onViewCreated(view, savedInstanceState);

    }
}