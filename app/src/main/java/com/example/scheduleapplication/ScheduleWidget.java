package com.example.scheduleapplication;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.room.Room;

import com.example.scheduleapplication.databases.AppDatabase;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;
import com.example.scheduleapplication.repositories.DayDao;
import com.example.scheduleapplication.repositories.LessonDao;
import com.example.scheduleapplication.service.DataService;

public class ScheduleWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class,"myBase").allowMainThreadQueries().build();
        DayDao dd = database.dayDao();
        LessonDao ld = database.lessonDao();
        Day curr = dd.getAll().get(MainActivity.currentDay);
        curr.getLessons().addAll(ld.getAll(curr.getId()));
//        MainActivity.database =  Room.databaseBuilder(this, AppDatabase::class.java, "MyDatabase").allowMainThreadQueries().build()
        // Construct the RemoteViews object
        @SuppressLint("RemoteViewLayout") RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.schedule_widget);
        views.setTextViewText(R.id.tv_day_widjet, curr.getNameDay());
        views.setTextViewText(R.id.tv_date_widjet, curr.getDate());

        Lesson l1 = curr.getLessons().get(0);
        if(l1!=null){
            views.setTextViewText(R.id.hour_1, l1.getTime());
            views.setTextViewText(R.id.room_1, l1.getOffice());
            views.setTextViewText(R.id.name_1, l1.getCourse());
            views.setTextViewText(R.id.room2_1, l1.getOfficeM2());
            views.setTextViewText(R.id.type_1, l1.getType());
            views.setTextViewText(R.id.teacher_1, l1.getTeacher());
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}