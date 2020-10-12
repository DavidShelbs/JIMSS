package com.example.usersetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

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

    TextView disPlaySpinnerValues;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightInput = (EditText) findViewById(R.id.weight);
        heightInput = (EditText) findViewById(R.id.height);
        goalInput = (Spinner) findViewById(R.id.goal);
        levelInput = (Spinner) findViewById(R.id.levels);
        nextButton = (Button) findViewById(R.id.Next);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.goal_entries, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.workout_level, android.R.layout.simple_spinner_item);

        goalInput.setAdapter(adapter1);
        levelInput.setAdapter(adapter2);

        goalInput.setOnItemSelectedListener(this);
        levelInput.setOnItemSelectedListener(this);

        disPlaySpinnerValues = (TextView) findViewById(R.id.background);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightLBS = Integer.valueOf(weightInput.getText().toString());
                heightLBS = Integer.valueOf(heightInput.getText().toString());
                disPlaySpinnerValues.setText(goalFitVal+levelsFitVal);
                openNewActivity();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.goal){
            goalFitVal = parent.getSelectedItem().toString();
        }
        else if(spinner.getId() == R.id.levels){
            levelsFitVal = parent.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openNewActivity(){
        Intent intent = new Intent(this, NewActivity.class); //change NewActivity to the name of the class for the next activity
    }
}
