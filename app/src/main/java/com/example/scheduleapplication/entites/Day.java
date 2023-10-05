package com.example.scheduleapplication.entites;


import androidx.room.AutoMigration;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity

public class Day implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    @ColumnInfo(name="name")
    private String nameDay;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "date_of_added")
    private String dateOfAdded;

    @Ignore
    private final List<Lesson> lessons = new ArrayList<>();

    @Ignore
    public boolean isActive = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameDay() {
        return nameDay;
    }

    public void setNameDay(String nameDay) {
        this.nameDay = nameDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public String getDateOfAdded() {
        return dateOfAdded;
    }

    public void setDateOfAdded(String dateOfAdded) {
        this.dateOfAdded = dateOfAdded;
    }
}

