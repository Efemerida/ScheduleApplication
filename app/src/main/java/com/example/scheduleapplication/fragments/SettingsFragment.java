package com.example.scheduleapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.scheduleapplication.R;
import com.example.scheduleapplication.service.DataService;
import com.example.scheduleapplication.service.GroupService;
import com.example.scheduleapplication.service.ScheduleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SettingsFragment extends Fragment {

    HashMap<String, String> groups;
    Context context;

    GroupService groupService;

    public SettingsFragment(Context context) {
        super(R.layout.fragment_settings);

        this.context = context;

        groupService = new GroupService(context);

        Thread thread = new Thread(() -> {
            groups = GroupService.getAllGroup();
        });
        thread.start();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> selectedGroups = new ArrayList<>();
       // groups.keySet().stream().forEach(x->selectedGroups.add(x));

        for(String group: groups.keySet()){
            selectedGroups.add(group);
        }

        AutoCompleteTextView textView =view.findViewById(R.id.change_group);

        textView.setText(groupService.getGroupName());

        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                selectedGroups);

        textView.setAdapter(groupAdapter);

        Button btn_change = view.findViewById(R.id.ch_group_button);
        btn_change.setOnClickListener(view1 -> {
            Log.d("taggg", "get " + textView.getText().toString());
            groupService.setGroup(textView.getText().toString(), groups.get(textView.getText().toString()));
            Thread thread = new Thread(() -> {
                DataService dataService = DataService.initial(context);
                dataService.loadData();
            });
            thread.start();
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}