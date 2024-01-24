package com.example.scheduleapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.scheduleapplication.R;
import com.example.scheduleapplication.service.DataService;
import com.example.scheduleapplication.service.GroupService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

        selectedGroups.addAll(groups.keySet());

        AutoCompleteTextView textView =view.findViewById(R.id.change_group);

        textView.setOnItemClickListener((adapterView, view12, i, l) -> {

            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            groupService.setGroup(textView.getText().toString(), groups.get(textView.getText().toString()));
            Thread thread = new Thread(() -> {
                DataService dataService = DataService.initial(context);
                dataService.loadData();
            });
            thread.start();
        });

        textView.setText(groupService.getGroupName());

        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                selectedGroups);

        textView.setAdapter(groupAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}