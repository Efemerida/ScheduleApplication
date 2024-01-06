package com.example.scheduleapplication.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.HashMap;

import lombok.SneakyThrows;

public class GroupService {

    private Context context;

    public GroupService(Context context) {
        this.context = context;
    }

    public void setGroup(String name, String link){
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(context);
        Log.d("taggg", "put " + link);
        myPreferences
                .edit()
                .putString("name", name)
                .putString("link", link)
                .commit();
    }

    public String getGroupName(){
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(context);
        return myPreferences.getString("name", "");
    }

    public String getLink(){
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(context);
        return myPreferences.getString("link", null);
    }


    public static HashMap<String, String> getAllGroup()  {
        HashMap<String, String> listGroups = new HashMap<>();

        try {
            Document document = Jsoup.connect("https://rasp.sstu.ru").get();
            Elements elements = document.select("div.group");
            elements.stream().forEach(x -> listGroups.put(x.text(), x.select("a").attr("href")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.d("taggg", "get " + listGroups);
        return listGroups;
    }

}
