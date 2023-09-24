package com.example.scheduleapplication.service;

import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;
import com.example.scheduleapplication.exceptions.NoConnectionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleService {

    private static ScheduleService scheduleService;

    private ScheduleService(){}

    public static ScheduleService initial(){
        if(scheduleService==null){
            scheduleService = new ScheduleService();
        }
        return scheduleService;
    }

    public List<Day> getContent(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://rasp.sstu.ru/rasp/group/14").get();
        } catch (IOException e) {
            throw new NoConnectionException();
        }
        return getDays(doc);

    }


    private List<Day> getDays(Document document){

        List<Day> daysList = new ArrayList<>();

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
                daysList.add(dayTmp);
            }

        }
        return daysList;


    }


}
