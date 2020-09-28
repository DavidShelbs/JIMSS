package com.example.workoutappdemo1;

import android.os.Bundle;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A basic example of how to use week view library.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class BasicActivity extends DetailActivity {


    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        Bundle extra = getIntent().getExtras();
        String name = extra.getString("EXTRA_NAME");
        String monday = extra.getString("EXTRA_MONDAY");
        String tuesday = extra.getString("EXTRA_TUESDAY");
        String wednesday = extra.getString("EXTRA_WEDNESDAY");
        String thursday = extra.getString("EXTRA_THURSDAY");
        String friday = extra.getString("EXTRA_FRIDAY");
        String saturday = extra.getString("EXTRA_SATURDAY");
        String sunday = extra.getString("EXTRA_SUNDAY");

        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        events.addAll(scheduler(monday, newYear, newMonth));
        events.addAll(scheduler(tuesday, newYear, newMonth));
        events.addAll(scheduler(wednesday, newYear, newMonth));
        events.addAll(scheduler(thursday, newYear, newMonth));
        events.addAll(scheduler(friday, newYear, newMonth));
        events.addAll(scheduler(saturday, newYear, newMonth));
        events.addAll(scheduler(sunday, newYear, newMonth));


        return events;
    }

    public static boolean isInteger(char s) {
        try {
            Integer.parseInt(Character.toString(s));
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        return true;
    }

    public List<WeekViewEvent> scheduler (String day, int newYear, int newMonth){
        // Init
        Calendar startTime;
        Calendar endTime;
        WeekViewEvent event;
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        //
        boolean busy = true;
        boolean workout = true;
        int index = 0;
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

                    endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, end_hr);
                    endTime.set(Calendar.MINUTE, end_min);
                    endTime.set(Calendar.MONTH, newMonth - 1);
                    event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_02));
                    events.add(event);

                    index += 9;
                }
                else{
                    busy = false;
                    index++;
                }
            }

            // Workout
            else{
//                System.out.print("\n\n\n" + index+ "\n\n\n");
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

                    endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, end_hr);
                    endTime.set(Calendar.MINUTE, end_min);
                    endTime.set(Calendar.MONTH, newMonth - 1);
                    event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_03));
                    events.add(event);

                    index += 9;
                }
                else{
                    workout = false;
                }
            }
        }



        return events;
    }

}
