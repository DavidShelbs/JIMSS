package com.example.jimssgym;

import android.os.Bundle;

import com.alamkanak.weekview.WeekViewEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A basic example of how to use week view library.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class FillCalenderActivity extends CalenderActivity {

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        Bundle extra = getIntent().getExtras();
        String monday = extra.getString("EXTRA_MONDAY");
        String tuesday = extra.getString("EXTRA_TUESDAY");
        String wednesday = extra.getString("EXTRA_WEDNESDAY");
        String thursday = extra.getString("EXTRA_THURSDAY");
        String friday = extra.getString("EXTRA_FRIDAY");
        String saturday = extra.getString("EXTRA_SATURDAY");
        String sunday = extra.getString("EXTRA_SUNDAY");

        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        // Figure out day
        int mon_cal, tues_cal, wed_cal, thurs_cal, fri_cal, sat_cal, sun_cal;
        LocalDate date = LocalDate.now().plusDays(0);
        int cal_day = date.getDayOfMonth();
        String day = date.format(DateTimeFormatter.ofPattern("EEEE"));

        // Set other days
        if (day.equals("Monday")){
            mon_cal = cal_day;
            tues_cal = cal_day+1;
            wed_cal = cal_day+2;
            thurs_cal = cal_day+3;
            fri_cal = cal_day+4;
            sat_cal = cal_day+5;
            sun_cal = cal_day+6;
        }
        else if (day.equals("Tuesday")){
            mon_cal = cal_day+6;
            tues_cal = cal_day;
            wed_cal = cal_day+1;
            thurs_cal = cal_day+2;
            fri_cal = cal_day+3;
            sat_cal = cal_day+4;
            sun_cal = cal_day+5;
        }
        else if (day.equals("Wednesday")){
            mon_cal = cal_day+5;
            tues_cal = cal_day+6;
            wed_cal = cal_day;
            thurs_cal = cal_day+1;
            fri_cal = cal_day+2;
            sat_cal = cal_day+3;
            sun_cal = cal_day+4;
        }
        else if (day.equals("Thursday")){
            mon_cal = cal_day+4;
            tues_cal = cal_day+5;
            wed_cal = cal_day+6;
            thurs_cal = cal_day;
            fri_cal = cal_day+1;
            sat_cal = cal_day+2;
            sun_cal = cal_day+3;
        }
        else if (day.equals("Friday")){
            mon_cal = cal_day+3;
            tues_cal = cal_day+4;
            wed_cal = cal_day+5;
            thurs_cal = cal_day+6;
            fri_cal = cal_day;
            sat_cal = cal_day+1;
            sun_cal = cal_day+2;
        }
        else if (day.equals("Saturday")){
            mon_cal = cal_day+2;
            tues_cal = cal_day+3;
            wed_cal = cal_day+4;
            thurs_cal = cal_day+5;
            fri_cal = cal_day+6;
            sat_cal = cal_day;
            sun_cal = cal_day+1;
        }
        else{
            mon_cal = cal_day+1;
            tues_cal = cal_day+2;
            wed_cal = cal_day+3;
            thurs_cal = cal_day+4;
            fri_cal = cal_day+5;
            sat_cal = cal_day+6;
            sun_cal = cal_day;
        }

        // Fill events
        events.addAll(scheduler(monday, newYear, newMonth, mon_cal));
        events.addAll(scheduler(tuesday, newYear, newMonth, tues_cal));
        events.addAll(scheduler(wednesday, newYear, newMonth, wed_cal));
        events.addAll(scheduler(thursday, newYear, newMonth, thurs_cal));
        events.addAll(scheduler(friday, newYear, newMonth, fri_cal));
        events.addAll(scheduler(saturday, newYear, newMonth, sat_cal));
        events.addAll(scheduler(sunday, newYear, newMonth, sun_cal));


        return events;
    }

    public List<WeekViewEvent> scheduler (String day, int newYear, int newMonth, int cal_day){
        // Init and set variables
        Calendar startTime;
        Calendar endTime;
        WeekViewEvent event;
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        boolean busy = true;
        boolean workout = true;
        int index = 0;
        int event_id = 1;

        // Populate day
        while (busy || workout){
            // Busy
            if (busy) {
                if (day.charAt(index) != ';'){
                    if (day.charAt(index) == ','){
                        index++;
                    }

                    int start_hr = Integer.parseInt(day.substring(index, index+2));
                    int start_min = Integer.parseInt(day.substring(index+2, index+4));
                    int end_hr = Integer.parseInt(day.substring(index+5, index+7));
                    int end_min = Integer.parseInt(day.substring(index+7, index+9));

                    startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, start_hr);
                    startTime.set(Calendar.MINUTE, start_min);
                    startTime.set(Calendar.MONTH, newMonth - 1);
                    startTime.set(Calendar.YEAR, newYear);
                    startTime.set(Calendar.DAY_OF_MONTH, cal_day);

                    endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, end_hr);
                    endTime.set(Calendar.MINUTE, end_min);
                    endTime.set(Calendar.MONTH, newMonth - 1);
                    endTime.set(Calendar.DAY_OF_MONTH, cal_day);

                    event = new WeekViewEvent(event_id, getEventTitle(startTime), startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_02));
                    events.add(event);
                    event.setName("Busy");

                    index += 9;
                    event_id++;
                }
                else{
                    busy = false;
                    index++;
                }
            }

            // Workout
            else{
                if (day.charAt(index) != ';'){
                    if (day.charAt(index) == ','){
                        index++;
                    }

                    int start_hr = Integer.parseInt(day.substring(index, index+2));
                    int start_min = Integer.parseInt(day.substring(index+2, index+4));
                    int end_hr = Integer.parseInt(day.substring(index+5, index+7));
                    int end_min = Integer.parseInt(day.substring(index+7, index+9));

                    startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, start_hr);
                    startTime.set(Calendar.MINUTE, start_min);
                    startTime.set(Calendar.MONTH, newMonth - 1);
                    startTime.set(Calendar.YEAR, newYear);
                    startTime.set(Calendar.DAY_OF_MONTH, cal_day);

                    endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, end_hr);
                    endTime.set(Calendar.MINUTE, end_min);
                    endTime.set(Calendar.MONTH, newMonth - 1);
                    endTime.set(Calendar.DAY_OF_MONTH, cal_day);
                    event = new WeekViewEvent(event_id, getEventTitle(startTime), startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_03));
                    events.add(event);
                    event.setName("WOD");

                    index += 9;
                    event_id++;
                }
                else{
                    workout = false;
                }
            }
        }

        return events;
    }

}
