package com.example.scheduleapplication.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.example.scheduleapplication.MainActivity;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.exceptions.NoConnectionException;
import com.example.scheduleapplication.fragments.PageFragment;
import com.example.scheduleapplication.service.DataService;
import com.example.scheduleapplication.service.DayService;
import com.example.scheduleapplication.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;

public class DayAdapterFragment  extends FragmentStateAdapter {

    List<Day> days = new ArrayList<>();



    public DayAdapterFragment(FragmentActivity fragmentActivity, Context context){
        super(fragmentActivity);

        Runnable runnable = () -> {
            DataService dataService = DataService.initial(context);
            DayService dayService = new DayService();
            dayService.setDays(days);
            if(!dataService.isActual()){
                try {
                    dataService.loadData();
                    fragmentActivity.runOnUiThread(() ->{
                        Toast toast = Toast.makeText(context, "Обновление данных", Toast.LENGTH_LONG);
                        toast.show();
                    });

                }catch (NoConnectionException ex){
                    fragmentActivity.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(context, "Нет подключение к сети", Toast.LENGTH_LONG);
                        toast.show();
                    });
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new PageFragment(position, days);
    }

    @Override
    public int getItemCount() {
        return 12;
    }
}
