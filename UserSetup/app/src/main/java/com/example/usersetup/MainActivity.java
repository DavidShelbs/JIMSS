package com.example.usersetup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_main);

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


//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                weightLBS = Integer.valueOf(weightInput.getText().toString());
//                heightLBS = Integer.valueOf(heightInput.getText().toString());
////                disPlaySpinnerValues.setText(goalFitVal+levelsFitVal);
//                openNewActivity();
//            }
//        });
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
//        Intent intent = new Intent(this, NewActivity.class); //change NewActivity to the name of the class for the next activity
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
            ((TextView) levelInput.getSelectedView()).setText("Must choose a goal");
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
        MainActivity.this.finish();
        System.exit(0);
    }
}







