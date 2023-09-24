package com.example.scheduleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.scheduleapplication.adapters.DayAdapterFragment;
import com.example.scheduleapplication.adapters.DayListAdapter;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;
import com.example.scheduleapplication.service.DayService;
import com.example.scheduleapplication.service.ScheduleService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        recyclerView = findViewById(R.id.day_list);

        List<Day> days = new ArrayList<>();

        getSupportActionBar().hide();

        DayAdapterFragment dayAdapterFragment = new DayAdapterFragment(this, this);
        DayService dayService = new DayService();
        dayService.setDays(days);
        Log.d("taggg", days.toString());

        DayListAdapter.OnStateClickListener onStateClickListener = new DayListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(int position) {
                Log.d("taggg", String.valueOf(position));
                viewPager2.setCurrentItem(position);
            }
        };

        recyclerView.setAdapter(new DayListAdapter(this, days, dayAdapterFragment, onStateClickListener));

        viewPager2.setAdapter(dayAdapterFragment);



    }
}