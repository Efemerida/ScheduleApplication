package com.example.scheduleapplication.service;

import android.util.Log;

import com.example.scheduleapplication.entites.Day;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DayService {

    public void setDays(List<Day> days){

        LocalDate localDate = LocalDate.now();

        if(localDate.getDayOfWeek().getValue()==7){
            localDate = localDate.plusDays(1);
        }
        else {
            while (localDate.getDayOfWeek().getValue() != 1) {
                localDate = localDate.minusDays(1);
            }
            Log.d("taggg", localDate.toString());
        }

        for(int i = 0; i < 12; i++){
            Day day = new Day();
            day.setDate(localDate.format(DateTimeFormatter.ofPattern("dd:MM")));
            day.setNameDay(getNameOfDay(localDate.getDayOfWeek().getValue()));
            days.add(day);
            localDate = localDate.plusDays(1);
            if(localDate.getDayOfWeek().getValue()==7)
                localDate = localDate.plusDays(1);
        }


    }

    private String getNameOfDay(int value){

        switch (value){
            case 1: return "Пн";
            case 2: return "Вт";
            case 3: return "Ср";
            case 4: return "Чт";
            case 5: return "Пт";
            case 6: return "Сб";
        }
        return null;
    }



}
