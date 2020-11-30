package com.example.jimssgym;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        


    }


}