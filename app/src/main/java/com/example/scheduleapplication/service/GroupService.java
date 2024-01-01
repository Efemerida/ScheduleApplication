package com.example.scheduleapplication.service;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.HashMap;

import lombok.SneakyThrows;

public class GroupService {

    public static HashMap<String, String> getAllGroup()  {
        HashMap<String, String> listGroups = new HashMap<>();

        try {
            Document document = Jsoup.connect("https://rasp.sstu.ru").get();
            Elements elements = document.select("div.group");
            elements.stream().forEach(x -> listGroups.put(x.text(), x.select("a").attr("href")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listGroups;
    }

}
