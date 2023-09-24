package com.example.scheduleapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapplication.R;
import com.example.scheduleapplication.adapters.DayAdapterFragment;
import com.example.scheduleapplication.adapters.DayListAdapter;
import com.example.scheduleapplication.adapters.LessonAdapter;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;
import com.example.scheduleapplication.service.DataService;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends Fragment {

    private int position;
    private List<Day> days;


    public PageFragment(int position, List<Day> days) {
        super(R.layout.main_fragment);
        this.position = position;
        this.days = days;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Context context = getActivity().getApplicationContext();
        DataService dataService =DataService.initial(context);


        List<Lesson> lessons = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.listLesson);
        ArrayList<Day> arrayList = new ArrayList<>();
        LessonAdapter lessonAdapter = new LessonAdapter(context, lessons);
        recyclerView.setAdapter(lessonAdapter);

        Runnable runnable = () -> {
            arrayList.addAll(dataService.getAll());

            lessons.addAll(arrayList.get(position).getLessons());

            recyclerView.post(lessonAdapter::notifyDataSetChanged);

        };
        Thread thread = new Thread(runnable);
        thread.start();

    }
}
