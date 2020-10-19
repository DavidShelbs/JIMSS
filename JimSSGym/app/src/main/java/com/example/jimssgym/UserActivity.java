package com.example.jimssgym;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private Button schedule_button;
    private Button edit_workout_button;
    private Button settings_button;

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
        setContentView(R.layout.activity_user);
//        lv = findViewById(R.id.lv);

        nameList = new ArrayList<>();
        mondayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
        tuesdayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
        wednesdayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
        thursdayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
        fridayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
        saturdayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
        sundayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
        for (int i = 0; i<mondayList.size(); i++){
            String str = mondayList.get(i).getName();
            nameList.add(str);
        }

//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
//        lv.setAdapter((ListAdapter) adapter);
//        lv.setOnItemClickListener(this);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        schedule_button = findViewById(R.id.schedule);
        edit_workout_button = findViewById(R.id.edit_workout);
        settings_button = findViewById(R.id.settings);


        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(UserActivity.this, UserScheduleActivity.class));

                Intent intent = new Intent(UserActivity.this, FillCalenderActivity.class);
                String name = mondayList.get(0).getName();
                String monday = mondayList.get(0).getMonday();
                String tuesday = tuesdayList.get(0).getTuesday();
                String wednesday = wednesdayList.get(0).getWednesday();
                String thursday = thursdayList.get(0).getThursday();
                String friday = fridayList.get(0).getFriday();
                String saturday = saturdayList.get(0).getSaturday();
                String sunday = sundayList.get(0).getSunday();

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
        });
        edit_workout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(UserActivity.this, UserActivity.class));

            }
        });
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(UserActivity.this, UserActivity.class));

            }
        });


    }
}