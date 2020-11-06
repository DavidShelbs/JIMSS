package com.example.jimssgym;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class PopUpAddTime extends Activity {

    private TextView popUp;


    private static final String TAG = "PopUpAddTime";
    private TextView mDisplayStart;
    private TextView mDisplayEnd;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String clickedTime = getIntent().getStringExtra("POPTYPE");


        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));


        setContentView(R.layout.activity_popupaddtime);

        popUp = findViewById(R.id.date_selected);
        popUp.setText(clickedTime.substring(clickedTime.length() - 5));


        // Scroll select time
        mDisplayStart = findViewById(R.id.startTime);
        mDisplayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog dialog = new TimePickerDialog(PopUpAddTime.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                int mHour = hourOfDay;
                                int mMinute = minute;

                                mDisplayStart.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);

                dialog.show();
            }
        });

        mDisplayEnd = findViewById(R.id.endTime);
        mDisplayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog dialog = new TimePickerDialog(PopUpAddTime.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                int mHour = hourOfDay;
                                int mMinute = minute;

                                mDisplayEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);

                dialog.show();
            }
        });


        // Toggle button
        ToggleButton toggle = findViewById(R.id.toggle_button);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });



        // Submit button
        Button button = findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopUpAddTime.this, "Added to your calender", Toast.LENGTH_SHORT).show();
            }
        });



    }

}




