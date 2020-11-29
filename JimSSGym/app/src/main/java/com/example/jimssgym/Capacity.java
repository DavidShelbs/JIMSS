package com.example.jimssgym;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Capacity extends AppCompatActivity{

    BarChart barChart;
    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> barEntries;

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
    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity);

        LineChart lineChart = (LineChart) findViewById(R.id.chart);


        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new BarEntry(0f,20));
        entries.add(new BarEntry(1f,15));
        entries.add(new BarEntry(1.5f, 24));
        entries.add(new BarEntry(3f,2));
        entries.add(new BarEntry(4f,10));
        entries.add(new BarEntry(5f,28));
        entries.add(new BarEntry(6f,50));
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
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



        ArrayList<Integer> capacity_nums = new ArrayList<Integer>();
        ArrayList<String> dates = new ArrayList<String>();

        ArrayList<DataEntry> data_entries = new ArrayList<>();

        String temp = "";


        try {
            File myObj = new File("/src/main/assets/log.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                temp = myReader.nextLine();
                String[] temp_entries = temp.split(",");
                String[] date_parse = temp_entries[1].split("= ");

                //add exception handling for when capacity is an empty string
                int cap = Integer.parseInt(temp_entries[0]);
                String date_time = date_parse[1];

                capacity_nums.add(cap);
                dates.add(date_time);
            }
            myReader.close();

            for(int i=0; i<capacity_nums.size(); i++){
                DataEntry tem = new DataEntry(capacity_nums.get(i), dates.get(i));
                data_entries.add(tem);
            }

            for(int i=0; i<capacity_nums.size(); i++){
                System.out.println(data_entries.get(i).weekday);
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }






    /*

    public void createRandomBarGraph (String Date1, String Date2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try{
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate1.setTime(date2);

            dates = new ArrayList<>();
            dates = getList(mDate1, mDate2);

            barEntries = new ArrayList<>();
            float max = 0f;
            float value = 0f;
            random = new Random();
            for(int j=0; j<dates.size(); j++){
                max = 100f;
                value = random.nextFloat()*max;
                barEntries.add(new BarEntry(value, j));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
        BarData barData = new BarData(dates, barDataSet);
        barChart.setData(barData);
    }

    public ArrayList<String> getList(Calendar startDate, Calendar endDate){
        ArrayList<String> list = new ArrayList<>();
        while(startDate.compareTo(endDate)<=0){
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    }

    public String getDate(Calendar cld){
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                + cld.get(Calendar.DAY_OF_MONTH);

        try{
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate = new SimpleDateFormat("yyy/MM/dd").format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return curDate;
    }
    */


}
