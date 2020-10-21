package com.example.workoutpage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chest = findViewById(R.id.chestButton);
        Button shoulders = findViewById(R.id.shoulderButton);
        Button back = findViewById(R.id.backButton);
        Button arms = findViewById(R.id.armsButton);
        Button legs = findViewById(R.id.legsButton);
        Button cardio = findViewById(R.id.cardioButton);

        chest.setOnClickListener(this);
        shoulders.setOnClickListener(this);
        back.setOnClickListener(this);
        arms.setOnClickListener(this);
        legs.setOnClickListener(this);
        cardio.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chestButton:
                Toast.makeText(this, "Chest selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shoulderButton:
                Toast.makeText(this, "Shoulder selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backButton:
                Toast.makeText(this, "Back selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.armsButton:
                Toast.makeText(this, "Arms selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.legsButton:
                Toast.makeText(this, "Leg selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cardioButton:
                Toast.makeText(this, "Cardio selected", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
