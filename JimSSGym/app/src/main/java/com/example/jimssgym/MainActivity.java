package com.example.jimssgym;

import android.content.Intent;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.*;


public class MainActivity extends AppCompatActivity {

    BarChart barChart;
    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> barEntries;

    private FirebaseAuth mAuth;

    public static SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yy", java.util.Locale.ENGLISH);

    public static class DataEntry {
        int capacity_number;
        String date_time;
        int weekday;
        int hour;
        int minute;
        double x_value;

        public DataEntry(int x, String y){
            capacity_number = x;
            date_time = y;

            hour = Integer.parseInt(y.substring(9,11));
            minute = Integer.parseInt(y.substring(12,14));

            try{
                Date my_date = fmt.parse(y.substring(0,8));
                weekday = getDayNumberOld(my_date);
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        public float getFloatValue(){
            float hour_value = hour - 6;
            hour_value = hour_value / 3.0f;

            float minute_value = minute / 180.0f;

            return hour_value + minute_value;
        }
    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //ImageView cal = (ImageView)findViewById(R.id.calendar);
        //cal.bringToFront();

        ArrayList<Integer> capacity_nums = new ArrayList<Integer>();
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<DataEntry> data_entries = new ArrayList<>();
        String temp = "";

        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("log.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready()) {
                temp = reader.readLine();
                String[] entries = temp.split(",");
                String[] date_parse = entries[1].split("= ");
                //add exception handling for when capacity is an empty string
                int cap = Integer.parseInt(entries[0]);
                String date_time = date_parse[1];

                capacity_nums.add(cap);
                dates.add(date_time);
            }
        }
        catch (IOException e){
            System.out.println("An error occurred.");

        }

        for(int i=0; i<capacity_nums.size(); i++){
            DataEntry tem = new DataEntry(capacity_nums.get(i), dates.get(i));
            data_entries.add(tem);
        }





        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();

        for(int i=0; i<capacity_nums.size(); i++) {
            String date_info = data_entries.get(i).date_time;
            int iend = date_info.indexOf(":");
            String abrv_date = date_info.substring(0, iend);

            //Showing a good day of data for demo purposes
            if (abrv_date.equals("11/16/20")) {
                float x = data_entries.get(i).getFloatValue();
                int y = data_entries.get(i).capacity_number;
                System.out.println(data_entries.get(i).getFloatValue());
                System.out.println(data_entries.get(i).capacity_number);
                entries.add(new BarEntry(x, y));
            }
        }




        LineDataSet lineDataSet = new LineDataSet(entries,"Dates");

        LineData theData = new LineData(lineDataSet);
        lineChart.setData(theData);
        lineChart.invalidate();

        final String[] quarters = new String[] { "6a", "9a", "12p", "3p", "6p", "9p", "12p"};


        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setAxisMaximum(6);
        xAxis.setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings:
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.workout:
                        Intent intent2 = new Intent(MainActivity.this, ScanCardViewActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.qr:
                        Intent intent3 = new Intent(MainActivity.this, QRReaderQuickScanActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.friends:
                        Intent intent4 = new Intent(MainActivity.this, FriendsActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            setTitle("Hello, " + currentUser.getDisplayName() + "!");
        }
        else {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }
}