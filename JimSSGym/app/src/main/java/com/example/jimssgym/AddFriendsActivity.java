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

public class AddFriendsActivity extends Activity {

//    private TextView popUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String clickedType = getIntent().getStringExtra("POPTYPE");

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        setContentView(R.layout.activity_addfriends);

//        popUp = findViewById(R.id.type);
//        popUp.setText(clickedType);

//        // Submit button
//        Button button = findViewById(R.id.delete_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(PopUpDelete.this, "Deleted Event", Toast.LENGTH_SHORT).show();
//            }
//        });



    }

}




