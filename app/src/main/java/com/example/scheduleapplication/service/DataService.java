package com.example.scheduleapplication.service;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.scheduleapplication.databases.AppDatabase;
import  com.example.scheduleapplication.entites.*
        ;
import com.example.scheduleapplication.repositories.DayDao;
import com.example.scheduleapplication.repositories.LessonDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;

public class DataService {

    private static DataService dataService;
    private final DayDao dayDao;
    private final LessonDao lessonDao;

    private final ScheduleService scheduleService;

    public static DataService initial(Context context){
        if(dataService==null) dataService = new DataService(context);
        return dataService;
    }

    private DataService(Context context){
        AppDatabase appDatabase = Room.databaseBuilder(context,
                        AppDatabase.class, "myBase")
                .fallbackToDestructiveMigration()
                .build();
        dayDao = appDatabase.dayDao();
        lessonDao = appDatabase.lessonDao();
        scheduleService = ScheduleService.initial();
    }

    public void loadData(){
        List<Day> days = scheduleService.getContent();
        insert(days);
    }

    public boolean isActual(){

        List<Day> days = dayDao.getAll();
        if(days.isEmpty()) return false;
        Day day = dayDao.getAll().get(0);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("dd-MM-yyyy hh:mm:ss");
        try {
            Date oldDate = simpleDateFormat.parse(day.getDateOfAdded());
            Date nowDate = new Date();
            Log.d("taggg", oldDate.toString());
            Log.d("taggg", nowDate.toString());
            Log.d("taggg", String.valueOf(nowDate.getTime() - oldDate.getTime()));
            if(nowDate.getTime() - oldDate.getTime() >= 28800000){
                return false;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void insert(List<Day> days){
        this.deleteAll();

        String date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());

        for(Day day: days) {

            day.setDateOfAdded(date);
            int id = (int) dayDao.insert(day);
            for(Lesson lesson: day.getLessons()){
                lesson.setIdDay(id);
                lessonDao.insert(lesson);
            }
        }
    }

    public List<Day> getAll(){
        List<Day> days = dayDao.getAll();
        for(Day day: days){
            day.getLessons().addAll(lessonDao.getAll(day.getId()));
        }
        return days;
    }


    public void deleteAll() {
        dayDao.deleteAll();
    }
}
