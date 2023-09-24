package com.example.scheduleapplication.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.scheduleapplication.entites.Day;

import java.util.List;

public class DayHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myDB";
    public static final String TABLE_NAME = "days";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NAME = "name";

    public DayHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table " + TABLE_NAME);
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" + COLUMN_ID
                + " integer primary key AUTOINCREMENT," + COLUMN_NAME + " text," + COLUMN_DATE + " text" + ")");

    }

    public void add(SQLiteDatabase db, List<Day> dayList){
        this.delete(db);
        this.onCreate(db);
        for(Day day: dayList){
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID,day.getId());
            values.put(COLUMN_DATE, day.getDate());
            values.put(COLUMN_NAME,day.getNameDay());
            db.insert(TABLE_NAME,null,values);
        }
    }

    public void checkAll(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME,null);
        while (cursor.moveToNext()){
            Log.d("myTagg", String.valueOf(cursor.getLong(0)) + " "
                    + cursor.getString(1) + " "
                    + cursor.getString(2));
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void delete(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("delete from " + TABLE_NAME);
    }
}
