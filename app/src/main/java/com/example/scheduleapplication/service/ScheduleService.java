package com.example.scheduleapplication.service;

import android.content.Context;
import android.util.Log;

import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;
import com.example.scheduleapplication.exceptions.NoConnectionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleService {

    private static ScheduleService scheduleService;

    public static String defaultLink = "/rasp/group/14";
    private static final String BASE_LINK = "https://rasp.sstu.ru";

    private final GroupService groupService;


    private ScheduleService(Context context){groupService = new GroupService(context);}

    public static ScheduleService initial(Context context){
        if(scheduleService==null){
            scheduleService = new ScheduleService(context);
        }
        return scheduleService;
    }

    public List<Day> getContent(){
        Document doc;
        try {
            String link;
            String linkTmp = groupService.getLink();
            if(linkTmp==null) link = BASE_LINK + defaultLink;
            else link = BASE_LINK + linkTmp;
            Log.d("taggg", linkTmp + " " + link);
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            throw new NoConnectionException();
        }
        return insertIntoDate(getDays(doc));

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

    private List<Day> insertIntoDate(List<Day> days) {

        List<Day> daysResult = new ArrayList<>();
        LocalDate date = LocalDate.now();
        while (date.getDayOfWeek().getValue()!=1){
            date = date.minusDays(1);
        }

        boolean flag = false;
        int j = 1;
        if(days.size()>0) {
            for (int i = 0; i < 12; i++) {
                if (!flag) {
                    String day = String.valueOf(date.getDayOfMonth());
                    if (date.getDayOfMonth() < 10) day = "0" + day;
                    String month = String.valueOf(date.getMonthValue());
                    if(date.getMonthValue()<10) month = "0" + month;
                    String stringDate = day + "." + month;
                    if (stringDate.equals(days.get(0).getDate())) {
                        Log.d("taggg", "tttt " + i);
                        flag = true;
                        daysResult.add(days.get(0));
                    } else {
                        date = date.plusDays(1);
                        if(date.getDayOfWeek()== DayOfWeek.SUNDAY) i--;
                        else daysResult.add(new Day());
                    }
                } else {
                    if (days.size() == 6 && i >= 6) {
                        daysResult.add(new Day());
                    } else {
                        Log.d("taggg", "tttt " + j + " " + i);

                        daysResult.add(days.get(j));
                        j++;
                    }
                }
            }
        }
        else {
            for (int i = 0; i < 12; i++) {
                daysResult.add(new Day());
            }
        }
        return daysResult;
    }






}
