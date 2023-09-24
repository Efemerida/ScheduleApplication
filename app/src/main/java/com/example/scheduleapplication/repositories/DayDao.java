package com.example.scheduleapplication.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.scheduleapplication.entites.Day;

import java.util.List;

@Dao
public interface DayDao {

    @Query("select * from day")
    List<Day> getAll();

    @Delete
    void delete(Day...days);

    @Query("delete from day")
    void deleteAll();

    @Insert
    long insert(Day day);


}
