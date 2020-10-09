package com.example.workoutappdemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;

    private ArrayList<String> nameList;
    private ArrayList<ScheduleModel> mondayList;
    private ArrayList<ScheduleModel> tuesdayList;
    private ArrayList<ScheduleModel> wednesdayList;
    private ArrayList<ScheduleModel> thursdayList;
    private ArrayList<ScheduleModel> fridayList;
    private ArrayList<ScheduleModel> saturdayList;
    private ArrayList<ScheduleModel> sundayList;



    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        lv = findViewById(R.id.lv);

        nameList = new ArrayList<>();
        mondayList = DataHelper.loadWorkout(this);
        tuesdayList = DataHelper.loadWorkout(this);
        wednesdayList = DataHelper.loadWorkout(this);
        thursdayList = DataHelper.loadWorkout(this);
        fridayList = DataHelper.loadWorkout(this);
        saturdayList = DataHelper.loadWorkout(this);
        sundayList = DataHelper.loadWorkout(this);
        for (int i = 0; i<mondayList.size(); i++){
            String str = mondayList.get(i).getName();
            nameList.add(str);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        lv.setAdapter((ListAdapter) adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
        Intent intent = new Intent(FriendsActivity.this, FillCalenderActivity.class);
        String name = mondayList.get(pos).getName();
        String monday = mondayList.get(pos).getMonday();
        String tuesday = tuesdayList.get(pos).getTuesday();
        String wednesday = wednesdayList.get(pos).getWednesday();
        String thursday = thursdayList.get(pos).getThursday();
        String friday = fridayList.get(pos).getFriday();
        String saturday = saturdayList.get(pos).getSaturday();
        String sunday = sundayList.get(pos).getSunday();

        intent.putExtra("EXTRA_NAME", name);
        intent.putExtra("EXTRA_MONDAY", monday);
        intent.putExtra("EXTRA_TUESDAY", tuesday);
        intent.putExtra("EXTRA_WEDNESDAY", wednesday);
        intent.putExtra("EXTRA_THURSDAY", thursday);
        intent.putExtra("EXTRA_FRIDAY", friday);
        intent.putExtra("EXTRA_SATURDAY", saturday);
        intent.putExtra("EXTRA_SUNDAY", sunday);

        startActivity(intent);
    }
}
