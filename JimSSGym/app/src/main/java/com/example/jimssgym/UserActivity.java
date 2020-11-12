package com.example.jimssgym;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private Button schedule_button;
    private Button edit_workout_button;
    private Button settings_button;

    private ListView lv;

    private ArrayList<String> nameList;
    private ArrayList<ScheduleModel> mondayList;
    private ArrayList<ScheduleModel> tuesdayList;
    private ArrayList<ScheduleModel> wednesdayList;
    private ArrayList<ScheduleModel> thursdayList;
    private ArrayList<ScheduleModel> fridayList;
    private ArrayList<ScheduleModel> saturdayList;
    private ArrayList<ScheduleModel> sundayList;

    private Adapter adapter;

    private static final String TAG = "UserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
//        lv = findViewById(R.id.lv);

//        nameList = new ArrayList<>();
//        mondayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
//        tuesdayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
//        wednesdayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
//        thursdayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
//        fridayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
//        saturdayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
//        sundayList = DataHelper.loadWorkout(this, "dataYourSchedule.json");
//        for (int i = 0; i<mondayList.size(); i++){
//            String str = mondayList.get(i).getName();
//            nameList.add(str);
//        }


//        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final String current_user_id = mAuth.getUid();
//
//
//        DocumentReference docRef = db.collection("users").document(current_user_id + "/calendar/personal_calendar");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                String day = null;
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//
//
//                    }else{
//                        Log.d(TAG, "No such document");
//                    }
//
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });














//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
//        lv.setAdapter((ListAdapter) adapter);
//        lv.setOnItemClickListener(this);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        schedule_button = findViewById(R.id.schedule);
        edit_workout_button = findViewById(R.id.edit_workout);
        settings_button = findViewById(R.id.settings);


        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final String current_user_id = mAuth.getUid();


                DocumentReference docRef = db.collection("users").document(current_user_id + "/calendar/personal_calendar");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String day = null;
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                Intent intent = new Intent(UserActivity.this, FillCalenderActivity.class);
                                String monday = ";;";
                                String tuesday = ";;";
                                String wednesday = ";;";
                                String thursday = ";;";
                                String friday = ";;";
                                String saturday = ";;";
                                String sunday = ";;";

                                if (document.getString("monday") != null)
                                    monday = document.getData().get("monday").toString();
                                if (document.getString("tuesday") != null)
                                    tuesday = document.getData().get("tuesday").toString();
                                if (document.getString("wednesday") != null)
                                    wednesday = document.getData().get("wednesday").toString();
                                if (document.getString("thursday") != null)
                                    thursday = document.getData().get("thursday").toString();
                                if (document.getString("friday") != null)
                                    friday = document.getData().get("friday").toString();
                                if (document.getString("saturday") != null)
                                    saturday = document.getData().get("saturday").toString();
                                if (document.getString("sunday") != null)
                                    sunday = document.getData().get("sunday").toString();

                                intent.putExtra("EXTRA_NAME", "Your Calender");
                                intent.putExtra("EXTRA_MONDAY", monday);
                                intent.putExtra("EXTRA_TUESDAY", tuesday);
                                intent.putExtra("EXTRA_WEDNESDAY", wednesday);
                                intent.putExtra("EXTRA_THURSDAY", thursday);
                                intent.putExtra("EXTRA_FRIDAY", friday);
                                intent.putExtra("EXTRA_SATURDAY", saturday);
                                intent.putExtra("EXTRA_SUNDAY", sunday);

                                startActivity(intent);


                            }else{
                                Log.d(TAG, "No such document");
                            }

                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

            }
        });
        edit_workout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(UserActivity.this, UserActivity.class));

            }
        });
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(UserActivity.this, UserActivity.class));

            }
        });


    }
}