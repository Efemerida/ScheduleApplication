package com.example.scheduleapplication.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.scheduleapplication.entites.*;
import com.example.scheduleapplication.repositories.DayDao;
import com.example.scheduleapplication.repositories.LessonDao;

@Database(entities = {Day.class, Lesson.class}, version = 1, exportSchema = false)
public  abstract class AppDatabase extends RoomDatabase {
    public abstract DayDao dayDao();
    public abstract LessonDao lessonDao();
}
