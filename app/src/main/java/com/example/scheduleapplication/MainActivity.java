package com.example.scheduleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.scheduleapplication.adapters.DayAdapterFragment;
import com.example.scheduleapplication.adapters.DayListAdapter;
import com.example.scheduleapplication.adapters.ListAdapterTwo;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;
import com.example.scheduleapplication.service.DayService;
import com.example.scheduleapplication.service.GroupService;
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

    ListView listView;

    public static int positionCurrent = 0;
    public static int currentDay = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ////////////////////////////////////////
        //Test
        //////////////////////////////
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GroupService.getAllGroup();
            }
        });
        thread.start();

        /////////////////////////////////

//        FragmentContainerView fragmentContainerView = findViewById(R.id.rasp_container);
//        fragmentContainerView.

        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }

        int position = date.getDayOfWeek().getValue()-1;
        if(date.getDayOfWeek().getValue()==7) position+=1;
        positionCurrent = position;
        currentDay = position;


        viewPager2 = findViewById(R.id.viewPager);
        recyclerView = findViewById(R.id.day_list);
        listView = findViewById(R.id.list_list);

        List<Day> days = new ArrayList<>();

        getSupportActionBar().hide();

        DayAdapterFragment dayAdapterFragment = new DayAdapterFragment(this, this);
        DayService dayService = new DayService();
        dayService.setDays(days);
        Log.d("taggg", days.toString());
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.s_select_radius_border);
        Drawable drawablePassive = getResources().getDrawable(R.drawable.select_radius_border);

        DayListAdapter dayListAdapter;
        DayListAdapter.OnStateClickListener onStateClickListener = new DayListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(int position) {
                Log.d("taggg", String.valueOf(position));
                viewPager2.setCurrentItem(position);
                if(currentDay == positionCurrent  && position!=currentDay){
                    recyclerView.getAdapter().notifyItemChanged(positionCurrent, 3);
                }
                recyclerView.getAdapter().notifyItemChanged(position, 1);
                recyclerView.getAdapter().notifyItemChanged(positionCurrent, 0);
                Log.d("taggg", "pos and pos "+ positionCurrent + " " + currentDay);

                positionCurrent = position;



            }
        };
        dayListAdapter = new DayListAdapter(this, days, dayAdapterFragment, onStateClickListener, drawable, drawablePassive);
        recyclerView.setAdapter(dayListAdapter);
        viewPager2.setAdapter(dayAdapterFragment);
        viewPager2.setCurrentItem(positionCurrent);
        recyclerView.getAdapter().notifyItemChanged(currentDay, 3);
        recyclerView.offsetChildrenHorizontal(14);
        listView.setAdapter(new ListAdapterTwo(this,R.layout.day_fragment, days));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if(currentDay == positionCurrent && position!=currentDay){
                    recyclerView.getAdapter().notifyItemChanged(positionCurrent, 3);
                }
                recyclerView.getAdapter().notifyItemChanged(position, 1);
                recyclerView.getAdapter().notifyItemChanged(positionCurrent, 0);

//                LocalDate localDate = LocalDate.now();
//                DayOfWeek day = localDate.getDayOfWeek();
//                Log.d("taggg", "day is " + day.getValue());

                if(position>=6 && positionCurrent<6)
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                if(position<=5 && positionCurrent>=6)
                    recyclerView.smoothScrollToPosition(0);

                positionCurrent = position;
                super.onPageSelected(position);
            }
        });


    }
}