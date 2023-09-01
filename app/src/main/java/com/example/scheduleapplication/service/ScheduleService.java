package com.example.scheduleapplication.service;

import android.util.Log;

import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class ScheduleService {



    public static List<Day> getContent(List<Day> days){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://rasp.sstu.ru/rasp/group/14").get();
            Log.d("myTag", doc.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getDays(doc, days);

    }


    private static List<Day> getDays(Document document, List<Day> daysArray){


        Elements calendar = document.select("div.calendar");
        Elements weeks =  calendar.select("div.week");
        for(Element element : weeks){
            Elements days = element.select("div.day");
            for(Element day: days){
                String nameDay = day.select("div.day-header").text();
                if(nameDay.equals("Пары")) continue;
                Elements lessons = day.select("div.day-lesson");
                Day dayTmp = new Day();
                dayTmp.setNameDay(nameDay.substring(0,nameDay.length()-5));
                dayTmp.setDate(nameDay.substring(dayTmp.getNameDay().length()));
                int i = 0;
                for(Element lesson: lessons){
                    Lesson tmpLesson = new Lesson();
                    if(lesson.select("div.lesson-name").isEmpty()){
                        continue;
                    }
                    tmpLesson.setTime(lesson.select("div.lesson-hour").text());
                    tmpLesson.setTeacher(lesson.select("div.lesson-teacher").text());
                    tmpLesson.setOffice(lesson.select("div.lesson-room").text());
                    tmpLesson.setCourse(lesson.select("div.lesson-name").text());
                    tmpLesson.setOfficeM2(lesson.select("div.lesson-room mt-2").text());
                    tmpLesson.setType(lesson.select("div.lesson-type").text());
                    dayTmp.getLessons().add(tmpLesson);

                }
                daysArray.add(dayTmp);
            }

        }
        return daysArray;


    }


}
