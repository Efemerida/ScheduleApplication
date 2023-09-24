package com.example.scheduleapplication.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapplication.R;
import com.example.scheduleapplication.adapters.LessonAdapter;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;
import com.example.scheduleapplication.repositories.DayHelper;
import com.example.scheduleapplication.repositories.LessonHelper;
import com.example.scheduleapplication.service.DataService;
import com.example.scheduleapplication.service.ScheduleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageFragment extends Fragment {

    private int position;

    public PageFragment(int position) {
        super(R.layout.main_fragment);
        this.position = position;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Context context = getActivity().getApplicationContext();
        DataService dataService =DataService.initial(context);


        List<Lesson> lessons = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.listLesson);
        TextView dayName = view.findViewById(R.id.dayName);
        ArrayList<Day> arrayList = new ArrayList<>();
        TextView dayDate = view.findViewById(R.id.dayDate);
        LessonAdapter lessonAdapter = new LessonAdapter(context, lessons);
        recyclerView.setAdapter(lessonAdapter);

        Runnable runnable = () -> {
            arrayList.addAll(dataService.getAll());

//            DayHelper dayHelper = new DayHelper(getContext());
//            LessonHelper lessonHelper = new LessonHelper(getContext());
//            SQLiteDatabase sqLiteDatabase = dayHelper.getWritableDatabase();
//            //lessonHelper.onCreate(sqLiteDatabase);
//            //dayHelper.onCreate(sqLiteDatabase);
//            //dayHelper.add(sqLiteDatabase, arrayList);
//
//            //lessonHelper.addAll(sqLiteDatabase, arrayList);
//            dayHelper.checkAll(sqLiteDatabase);
//            lessonHelper.checkAll(sqLiteDatabase);

            lessons.addAll(arrayList.get(position).getLessons());

            recyclerView.post(lessonAdapter::notifyDataSetChanged);

            dayDate.post(()->dayDate.setText(arrayList.get(position).getDate()));
            dayName.post(()->dayName.setText(arrayList.get(position).getNameDay()));

        };
        Thread thread = new Thread(runnable);
        thread.start();

    }
}
