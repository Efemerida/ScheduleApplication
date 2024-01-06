package com.example.scheduleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.scheduleapplication.fragments.MainFragment;
import com.example.scheduleapplication.fragments.PageFragment;
import com.example.scheduleapplication.fragments.SettingsFragment;
import com.example.scheduleapplication.service.GroupService;

public class MainActivity extends AppCompatActivity {

    boolean is_settings = false;

    public static MainActivity mainActivity;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        MainActivity.mainActivity = this;

        Fragment mainFragment = new MainFragment();
        Fragment settFragment = new SettingsFragment(this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rasp_container, mainFragment)
                .commit();

        Button rasp = findViewById(R.id.b_nav_rasp);
        Button sett = findViewById(R.id.b_nav_set);

        sett.setOnClickListener(view -> {
            if(is_settings==false){
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(mainFragment)
                        .add(R.id.rasp_container, settFragment)
                        .commit();
            }
            is_settings = true;


        });

        rasp.setOnClickListener(view -> {
            if(is_settings==true){
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(settFragment)
                        .add(R.id.rasp_container, mainFragment)
                        .commit();
            }
            is_settings = false;
        });

        ////////////////////////////////////////
        //Test
        //////////////////////////////




        /////////////////////////////////




    }
}