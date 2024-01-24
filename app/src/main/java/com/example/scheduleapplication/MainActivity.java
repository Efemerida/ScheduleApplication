package com.example.scheduleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.example.scheduleapplication.fragments.MainFragment;
import com.example.scheduleapplication.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    boolean is_settings = false;

    public static MainActivity mainActivity;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        MainActivity.mainActivity = this;

        View content = findViewById(R.id.container);



        Fragment mainFragment = new MainFragment();
        Fragment settFragment = new SettingsFragment(this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rasp_container, mainFragment)
                .commit();

        Button rasp = findViewById(R.id.b_nav_rasp);
        Button sett = findViewById(R.id.b_nav_set);

        sett.setOnClickListener(view -> {
            if(!is_settings){
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(mainFragment)
                        .add(R.id.rasp_container, settFragment)
                        .commit();
            }
            is_settings = true;


        });


        content.getViewTreeObserver()
                .addOnPreDrawListener(() -> {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                });

        rasp.setOnClickListener(view -> {
            if(is_settings){
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