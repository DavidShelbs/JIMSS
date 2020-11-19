package com.example.jimssgym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class UserSetupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText weightInput;
    EditText heightInput;

    Spinner goalInput;
    Spinner levelInput;

    Button nextButton;

    float weightLBS = 0;
    float heightLBS = 0;

    String goalFitVal = "";
    String levelsFitVal = "";


//    TextView disPlaySpinnerValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setup);

        weightInput = (EditText) findViewById(R.id.weight);
        weightInput.setVisibility(View.VISIBLE);

        heightInput = (EditText) findViewById(R.id.height);
        heightInput.setVisibility(View.VISIBLE);

        goalInput = (Spinner) findViewById(R.id.goal);
        levelInput = (Spinner) findViewById(R.id.levels);
        nextButton = (Button) findViewById(R.id.Next);


//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.goal_entries, android.R.layout.simple_spinner_item);
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.workout_level, android.R.layout.simple_spinner_item);
//
//        goalInput.setAdapter(adapter1);
//        levelInput.setAdapter(adapter2);
//
//        goalInput.setOnItemSelectedListener(this);
//        levelInput.setOnItemSelectedListener(this);

//        disPlaySpinnerValues = (TextView) findViewById(R.id.background);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateWeight() | !validateHeight() | !validateGoal() | !validateLevel()) {
                    return;
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final String current_user_id = mAuth.getUid();
                Map<String, Object> user_workout = new HashMap<>();
                user_workout.put("goal", goalInput.getSelectedItem().toString());
                user_workout.put("level", levelInput.getSelectedItem().toString());
                user_workout.put("height", Integer.parseInt(weightInput.getText().toString()));
                user_workout.put("weight", Integer.parseInt(heightInput.getText().toString()));

                db.collection("users").document(current_user_id)
                        .update(user_workout)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("DocumentSnapshot added with ID: " + current_user_id);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Error adding document" + e);
                            }
                        });
                openNewActivity();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.goal) {
            goalFitVal = parent.getSelectedItem().toString();
        } else if (spinner.getId() == R.id.levels) {
            levelsFitVal = parent.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openNewActivity() {
        Intent intent = new Intent(this, MainActivity.class); //change NewActivity to the name of the class for the next activity
        startActivity(intent);
    }

    private boolean validateWeight() {
        String wI = weightInput.getText().toString();
        if (wI.isEmpty()) {
            weightInput.setError("Weight cannot be empty");
            return false;
        } else if (!(wI.matches("-?\\d+(\\.\\d+)?"))) {
            weightInput.setError("Weight must be a valid number with/without decimal");
            return false;
        } else {
            weightInput.setError(null);
            return true;
        }
    }

    private boolean validateHeight() {
        String hI = heightInput.getText().toString();
        if (hI.isEmpty()) {
            heightInput.setError("Height cannot be empty");
            return false;
        } else if (!(hI.matches("-?\\d+(\\.\\d+)?"))) {
            heightInput.setError("Height must be a valid number with/without decimal");
            return false;
        } else {
            heightInput.setError(null);
            return true;
        }
    }

    @SuppressLint("SetTextI18n")
    private boolean validateGoal() {
        String gI = goalInput.getSelectedItem().toString();
        if (gI.contentEquals("Select")) {
            ((TextView) goalInput.getSelectedView()).setTextColor(Color.RED);
            ((TextView) goalInput.getSelectedView()).setText("Must choose a goal");
            return false;
        } else {
            ((TextView) goalInput.getSelectedView()).setError(null);
            return true;
        }
    }

    @SuppressLint("SetTextI18n")
    private boolean validateLevel() {
        String lI = levelInput.getSelectedItem().toString();
        if (lI.contentEquals("Select")) {
            ((TextView) levelInput.getSelectedView()).setTextColor(Color.RED);
            ((TextView) levelInput.getSelectedView()).setText("Must choose a workout level");
            return false;
        } else {
            ((TextView) levelInput.getSelectedView()).setError(null);
            return true;
        }
    }

    public void confirmUserInput(View v) throws IOException {
        if (!validateWeight() | !validateHeight() | !validateGoal() | !validateLevel()) {
            return;
        }
        Log.i("Weight Info", weightInput.getText().toString());
        Log.i("Height Info", heightInput.getText().toString());
        Log.i("Goal Info", goalInput.getSelectedItem().toString());
        Log.i("Level Info", levelInput.getSelectedItem().toString());
//        File logFile = new File("Users/orangej/Documents/UserInfoLog.file");
//        if (!logFile.exists()) {
//            try {
//                logFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }
//        }
//        try {
//            BufferedWriter buff = new BufferedWriter(new FileWriter(logFile, true));
//            buff.append(weightInput.getText().toString());
//            buff.newLine();
//            buff.append(heightInput.getText().toString());
//            buff.newLine();
//            buff.append(goalInput.getSelectedItem().toString());
//            buff.newLine();
//            buff.append(levelInput.getSelectedItem().toString());
//            buff.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        UserSetupActivity.this.finish();
//        System.exit(0);
    }
}







