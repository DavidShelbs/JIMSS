package com.example.jimssgym;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PopUpWorkoutActivity extends Activity {

    private TextView workout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String poptype = getIntent().getStringExtra("POPTYPE");


        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
//        List<String> wod = new ArrayList<>();

        getWindow().setLayout((int)(width*.8), (int)(height*.6));


        setContentView(R.layout.activity_popupworkout);

        workout = findViewById(R.id.day_selected);
        workout.setText("Monday");

        workout = findViewById(R.id.wod);

        workout.setText("Bench: 4x5\nSit ups: 4x6\nRun a mile");




        // Get data
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final String current_user_id = mAuth.getUid();

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        final List<String> finalWod = wod;
//        db.collection("users/" + "fktWl1n5lQXZj3UkbalTY3UShxl2" + "/workouts/" + "monday" + "/exercises")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//
//                                InputStream machines_json = getResources().openRawResource(R.raw.exercises);
//                                BufferedReader machines_reader = new BufferedReader(new InputStreamReader(machines_json, Charset.forName("UTF-8")));
//                                String machines_lines = "";
//                                while (true) {
//                                    try {
//                                        if (!((machines_lines = machines_reader.readLine()) != null))
//                                            break;
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    JSONArray machinesArray = null;
//                                    try {
//                                        machinesArray = new JSONArray(machines_lines);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    for (int i = 0; i < machinesArray.length(); i++) {
//                                        try {
//                                            JSONObject machinesObject = machinesArray.getJSONObject(i);
//                                            String equipment_name = machinesObject.getString("name");
//                                            String equipment_id = machinesObject.getString("id");
//                                            if (equipment_id.equals(document.getId())) {
//                                                System.out.println("\n\n\n\n\n" + equipment_name + " => " + document.getData());
//                                                finalWod.add(equipment_name + ": " + document.getData() + "\n");
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//
//
//
//
//                                }
//                            }
//                            setContentView(R.layout.activity_popupworkout);
//                            workout = findViewById(R.id.day_selected);
//                            workout.setText("Monday");
//
//                            workout = findViewById(R.id.wod);
//
//                            workout.setText(finalWod.get(0) + finalWod.get(1));
//                        } else {
//                            System.out.println("Error getting documents: " + task.getException());
//                        }
//                    }
//                });








    }
}
