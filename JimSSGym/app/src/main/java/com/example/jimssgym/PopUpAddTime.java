package com.example.jimssgym;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
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
        final ToggleButton toggle = findViewById(R.id.toggle_button);
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
                if (!toggle.isChecked()){

                    String startTime = mDisplayStart.getText().toString().replace(":", "");
                    String endTime = mDisplayEnd.getText().toString().replace(":", "");
                    final String addToCalendar = startTime + "-" + endTime;

                    final String finalDay = LocalDate.of(2020, Integer.parseInt(popUp.getText().toString().substring(0, 2)), Integer.parseInt(popUp.getText().toString().substring(3, 5))).getDayOfWeek().toString().toLowerCase();

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
                                    if (document.getString(finalDay) != null){
                                        day = document.getData().get(finalDay).toString();
                                        if (';' == day.charAt(0)){
                                            day = addToCalendar + day;
                                        }
                                        else
                                            day = addToCalendar + "," + day;
                                    }
                                    else{
                                        Log.d(TAG, "Nothing in yet");
                                        day = addToCalendar + ";;";
                                    }

                                }else{
                                    Log.d(TAG, "No such document");
                                    day = addToCalendar + ";;";
                                }


                                // Create a new user with a first and last name
                                Map<String, Object> user_workout = new HashMap<>();
                                user_workout.put(finalDay, day);

                                // Add a new document with a generated ID
                                db.collection("users").document(current_user_id + "/calendar/personal_calendar")
                                        .update(user_workout)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                System.out.println("DocumentSnapshot added with ID: " + current_user_id);
                                                Toast.makeText(PopUpAddTime.this, "Added to your calender", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                System.out.println("Error adding document" + e);
                                            }
                                        });
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });







//                    Toast.makeText(PopUpAddTime.this, "Added to your calender", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

}




