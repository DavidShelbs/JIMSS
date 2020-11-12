package com.example.jimssgym;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PopUpDelete extends Activity {

    private TextView popUp;
    private static final String TAG = "UserActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String clickedType = getIntent().getStringExtra("POPTYPE");


        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        setContentView(R.layout.activity_popupdelete);

        popUp = findViewById(R.id.type);
        popUp.setText(clickedType);

        // Submit button
        Button button = findViewById(R.id.delete_button);
        button.setOnClickListener(new View.OnClickListener() {
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
                                day = document.getData().get(popUp.getText().toString()).toString();
                                Integer index = day.indexOf(";");
                                day = day.substring(index);


                            }else{
                                Log.d(TAG, "No such document");
                            }


                            // Create a new user with a first and last name
                            Map<String, Object> user_workout = new HashMap<>();
                            user_workout.put(popUp.getText().toString(), day);

                            // Add a new document with a generated ID
                            db.collection("users").document(current_user_id + "/calendar/personal_calendar")
                                    .update(user_workout)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            System.out.println("DocumentSnapshot added with ID: " + current_user_id);
                                            Toast.makeText(PopUpDelete.this, "Deleted Event", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(PopUpDelete.this, FillCalenderActivity.class));
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

            }
        });



    }

}




