package com.example.jimssgym;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PopUpWorkoutActivity extends Activity {

    private TextView workout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String poptype = getIntent().getStringExtra("POPTYPE");

        setContentView(R.layout.activity_popupworkout);
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));




        // Get data

        
        workout = findViewById(R.id.day_selected);
        workout.setText("Monday");

        workout = findViewById(R.id.wod);
        workout.setText("100 Pull-ups\n100 Push-ups\n100 Sit-ups\n100 Squats\nFor Time: Complete all reps of each exercise before moving to the next.");






    }
}
