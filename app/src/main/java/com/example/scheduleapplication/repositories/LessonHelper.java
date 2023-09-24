package com.example.scheduleapplication.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;

import java.util.List;

public class LessonHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myDB";
    private static final String TABLE_NAME = "lessons";

    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_OFFICE = "office";
    private static final String COLUMN_OFFICE2 = "office2";
    private static final String COLUMN_COURSE = "course";
    private static final String COLUMN_TEACHER = "teacher";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ID_DAY = "id_day";


    public LessonHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " + TABLE_NAME + "(" + COLUMN_ID
                + " integer primary key AUTOINCREMENT," + COLUMN_COURSE + " text,"
                + COLUMN_OFFICE + " text, " + COLUMN_OFFICE2 + " text, "
                + COLUMN_TEACHER + " text, " + COLUMN_TYPE + " text, "
                + COLUMN_TIME + " text, " + COLUMN_ID_DAY + " long)");

    }

    public void addAll(SQLiteDatabase db, List<Day> days){
//        this.delete(db);
//        this.onCreate(db);
//
//        for(Day day:days){
//
//            long id = day.getId();
//
//            for(Lesson lesson: day.getLessons()){
//
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(COLUMN_COURSE, lesson.getCourse());
//                contentValues.put(COLUMN_OFFICE, lesson.getOffice());
//                contentValues.put(COLUMN_ID_DAY, id);
//                contentValues.put(COLUMN_OFFICE2, lesson.getOfficeM2());
//                contentValues.put(COLUMN_TEACHER, lesson.getTeacher());
//                contentValues.put(COLUMN_TIME, lesson.getTime());
//                contentValues.put(COLUMN_TYPE, lesson.getType());
//
//                db.insert(TABLE_NAME, null, contentValues);
//
//            }
//        }
    }

    private void delete(SQLiteDatabase db) {
        db.execSQL("delete from " + TABLE_NAME);
    }

    public void checkAll(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        while (cursor.moveToNext()){
            Log.d("myTagg", String.valueOf(cursor.getLong(0)) + " "
            + cursor.getString(1) + " " + cursor.getString(2) + " "
                    + cursor.getString(3) + " " + cursor.getString(4) + " "
                    + cursor.getString(5) + " " + cursor.getString(6) + " "
                    + cursor.getLong(7));
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
