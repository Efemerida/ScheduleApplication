package com.example.scheduleapplication.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;

import java.util.List;

@Dao
public interface LessonDao {

    @Query("select * from lesson where idDay is :idDay")
    List<Lesson> getAll(int idDay);

    @Delete
    void delete(Lesson...lessons);

    @Query("delete from lesson")
    void deleteAll();

    @Insert
    void insert(Lesson lesson);


}
