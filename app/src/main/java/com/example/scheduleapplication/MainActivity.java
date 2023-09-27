package com.example.scheduleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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
    static RecyclerView recyclerView;

    public static int positionCurrent = -1;

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
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.s_select_radius_border);
        Drawable drawablePassive = getResources().getDrawable(R.drawable.select_radius_border);
        DayListAdapter.OnStateClickListener onStateClickListener = new DayListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(int position) {
                Log.d("taggg", String.valueOf(position));

                List<Object> objects = new ArrayList<>();
                objects.add(1);
                objects.add(recyclerView);

                viewPager2.setCurrentItem(position);

                if(positionCurrent!=-1){
                    recyclerView.getAdapter().notifyItemChanged(positionCurrent, 0);
                }
                recyclerView.getAdapter().notifyItemChanged(position, 1);
            }
        };
        recyclerView.setAdapter(new DayListAdapter(this, days, dayAdapterFragment, onStateClickListener, drawable, drawablePassive));
        viewPager2.setAdapter(dayAdapterFragment);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                if(positionCurrent!=-1){
                    recyclerView.getAdapter().notifyItemChanged(positionCurrent, 0);
                }
                recyclerView.getAdapter().notifyItemChanged(position, 1);

                super.onPageSelected(position);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalDate date = LocalDate.now();
        int position = date.getDayOfWeek().getValue()-1;
        viewPager2.setCurrentItem(position);

        recyclerView.getAdapter().notifyItemChanged(position, 1);


    }
}