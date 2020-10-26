package com.example.workoutbodyselection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    CheckBox chest, shoulder, back, arms, legs, cardio;
    Button workoutSubmit;

    public static Boolean chestChecked = false;
    public static Boolean shoulderChecked = false;
    public static Boolean backChecked = false;
    public static Boolean armsChecked = false;
    public static Boolean legsChecked = false;
    public static Boolean cardioChecked = false;

    public static Boolean buttonProceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chest = (CheckBox) findViewById(R.id.chestBox);
        shoulder = (CheckBox) findViewById(R.id.shoulderBox);
        back = (CheckBox) findViewById(R.id.backBox);
        arms = (CheckBox) findViewById(R.id.armsBox);
        legs = (CheckBox) findViewById(R.id.legsBox);
        cardio = (CheckBox) findViewById(R.id.cardioBox);
        workoutSubmit = (Button) findViewById(R.id.workout);
        Intent intent = new Intent(this, DoingTheWorkout.class); //change NewActivity to the name of the class for the next activity


        workoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBoxValidate())
                    return;

                if (chest.isChecked())
                    chestChecked = true;
                if (shoulder.isChecked())
                    shoulderChecked = true;
                if (back.isChecked())
                    backChecked = true;
                if (arms.isChecked())
                    armsChecked = true;
                if (legs.isChecked())
                    legsChecked = true;
                if (cardio.isChecked())
                    cardioChecked = true;
                startActivity(intent);

//                openNewActivity();
//                startActivity(intent);


                Log.i("chest Info", String.valueOf(chestChecked));
                Log.i("shoulder Info", String.valueOf(shoulderChecked));
                Log.i("back Info", String.valueOf(backChecked));
                Log.i("arms Info", String.valueOf(armsChecked));
                Log.i("legs Info", String.valueOf(legsChecked));
            }
        });
    }
    private boolean checkBoxValidate() {
        if (!chest.isChecked() && !shoulder.isChecked() && !back.isChecked() && !arms.isChecked() && !legs.isChecked() && !cardio.isChecked()) {
            workoutSubmit.setError("Must choose body group");
            return false;
        }
        else
        {
            workoutSubmit.setError(null);
            return true;
        }
    }
}








//    public void openNewActivity() {
//        Intent intent = new Intent(this, DoingTheWorkout.class); //change NewActivity to the name of the class for the next activity
//    }
//}

