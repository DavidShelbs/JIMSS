package com.example.jimssgym;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        //back button
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        nameList = new ArrayList<>();
        mondayList = DataHelper.loadWorkout(this, "dataFriends.json");
        tuesdayList = DataHelper.loadWorkout(this, "dataFriends.json");
        wednesdayList = DataHelper.loadWorkout(this, "dataFriends.json");
        thursdayList = DataHelper.loadWorkout(this, "dataFriends.json");
        fridayList = DataHelper.loadWorkout(this, "dataFriends.json");
        saturdayList = DataHelper.loadWorkout(this, "dataFriends.json");
        sundayList = DataHelper.loadWorkout(this, "dataFriends.json");
        for (int i = 0; i<mondayList.size(); i++){
            String str = mondayList.get(i).getName();
            nameList.add(str);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        lv.setAdapter((ListAdapter) adapter);
        lv.setOnItemClickListener(this);

        // Submit button
        Button button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(FriendsActivity.this, "Clicked add", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FriendsActivity.this, FriendCardViewActivity.class);
                startActivity(intent);
            }
        });
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
