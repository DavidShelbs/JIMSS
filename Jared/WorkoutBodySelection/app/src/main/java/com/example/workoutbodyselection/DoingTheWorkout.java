package com.example.workoutbodyselection;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.AsyncTaskLoader;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DoingTheWorkout extends AppCompatActivity {

    Button backButton;
    TextView setDisplay;
    TextView maxPercentage;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing_the_workout);
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference();


        backButton = (Button) findViewById(R.id.backButton);
        setDisplay = (TextView) findViewById(R.id.repView);
        maxPercentage = (TextView) findViewById(R.id.liftMax);

        ArrayList<String> chestList = new ArrayList<>();
        ArrayList<String> shoulderList = new ArrayList<>();
        ArrayList<String> backList = new ArrayList<>();
        ArrayList<String> armsList = new ArrayList<>();
        ArrayList<String> legList = new ArrayList<>();


        CheckBox chestCheckBox1 = findViewById(R.id.eCheckBox1);
        CheckBox chestCheckBox2 = findViewById(R.id.eCheckBox2);
        CheckBox chestCheckBox3 = findViewById(R.id.eCheckBox3);
        CheckBox chestCheckBox4 = findViewById(R.id.eCheckBox4);

        CheckBox shoulderCheckBox1 = findViewById(R.id.eCheckBox5);
        CheckBox shoulderCheckBox2 = findViewById(R.id.eCheckBox6);
        CheckBox shoulderCheckBox3 = findViewById(R.id.eCheckBox7);
        CheckBox shoulderCheckBox4 = findViewById(R.id.eCheckBox8);

        CheckBox backCheckBox1 = findViewById(R.id.eCheckBox9);
        CheckBox backCheckBox2 = findViewById(R.id.eCheckBox10);
        CheckBox backCheckBox3 = findViewById(R.id.eCheckBox11);
        CheckBox backCheckBox4 = findViewById(R.id.eCheckBox12);

        CheckBox armsCheckBox1 = findViewById(R.id.eCheckBox13);
        CheckBox armsCheckBox2 = findViewById(R.id.eCheckBox14);
        CheckBox armsCheckBox3 = findViewById(R.id.eCheckBox15);
        CheckBox armsCheckBox4 = findViewById(R.id.eCheckBox16);

        CheckBox legsCheckBox1 = findViewById(R.id.eCheckBox17);
        CheckBox legsCheckBox2 = findViewById(R.id.eCheckBox18);
        CheckBox legsCheckBox3 = findViewById(R.id.eCheckBox19);
        CheckBox legsCheckBox4 = findViewById(R.id.eCheckBox20);

        UserSetup.levelInputVar = "Beginner";
        UserSetup.goalInputVar = "Lose Weight";


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        File file = new File("exercises.txt");
        Scanner in = null;
        try {
            in = new Scanner(file);
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.contains("chest"))
                    chestList.add(line);
                if (line.contains("shoulders"))
                    shoulderList.add(line);
                if (line.contains("back") || line.contains("lats"))
                    backList.add(line);
                if (line.contains("biceps") || line.contains("triceps") || line.contains("forearms"))
                    armsList.add(line);
                if (line.contains("legs") || line.contains("hamstrings") || line.contains("quadriceps") || line.contains("calves"))
                    legList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


// ******************************* BEGINNER SET ***********************************************************************
        if (UserSetup.levelInputVar.equals("Beginner")) {
            maxPercentage.setText("60-80% of 1 rep max");

            if (UserSetup.goalInputVar.equals("Lose Weight")) {
                setDisplay.setText("Reps: 3x8");
                if (MainActivity.chestChecked) {
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);
                }

                if (MainActivity.backChecked) {
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                }
                if (MainActivity.legsChecked) {
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);
                }
            }


            if (UserSetup.goalInputVar.equals("Build Muscle")) {
                setDisplay.setText("Reps: 4x8");
                if (MainActivity.chestChecked) {
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);
                }

                if (MainActivity.backChecked) {
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                }
                if (MainActivity.legsChecked) {
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);
                }
            }

            if (UserSetup.goalInputVar.equals("Improve Definition")) {
                setDisplay.setText("Reps: 3x12");
                if (MainActivity.chestChecked) {
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);
                }

                if (MainActivity.backChecked) {
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                }
                if (MainActivity.legsChecked) {
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);
                }
            }

            if (UserSetup.goalInputVar.equals("Improve General Fitness")) {
                setDisplay.setText("Reps: 3x8");
                if (MainActivity.chestChecked) {
                    chestCheckBox3.setVisibility(View.INVISIBLE);
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox3.setVisibility(View.INVISIBLE);
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                }

                if (MainActivity.backChecked) {
                    backCheckBox3.setVisibility(View.INVISIBLE);
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox3.setVisibility(View.INVISIBLE);
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);
                }
                if (MainActivity.legsChecked) {
                    legsCheckBox3.setVisibility(View.INVISIBLE);
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);
                }
            }
        }

// ******************************* INTERMEDIATE SET ***********************************************************************

        if (UserSetup.levelInputVar.equals("Intermediate")) {
            maxPercentage.setText("60-80% of 1 rep max");

            if (UserSetup.goalInputVar.equals("Lose Weight")) {
                setDisplay.setText("Reps: 4x8");
                if (MainActivity.chestChecked) {
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);
                }

                if (MainActivity.backChecked) {
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                }
                if (MainActivity.legsChecked) {
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);
                }
            }

            if (UserSetup.goalInputVar.equals("Build Muscle")) {
                setDisplay.setText("Reps: 5x8");
                if (MainActivity.chestChecked) {
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);
                }

                if (MainActivity.backChecked) {
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                }
                if (MainActivity.legsChecked) {
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);
                }
            }

            if (UserSetup.goalInputVar.equals("Improve Definition")) {
                setDisplay.setText("Reps: 4x12");
                if (MainActivity.chestChecked) {
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);
                }

                if (MainActivity.backChecked) {
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                }
                if (MainActivity.legsChecked) {
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);
                }
            }

            if (UserSetup.goalInputVar.equals("Improve General Fitness")) {
                setDisplay.setText("Reps: 4x8");
                if (MainActivity.chestChecked) {
                    chestCheckBox3.setVisibility(View.INVISIBLE);
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox3.setVisibility(View.INVISIBLE);
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                }

                if (MainActivity.backChecked) {
                    backCheckBox3.setVisibility(View.INVISIBLE);
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox3.setVisibility(View.INVISIBLE);
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);
                }
                if (MainActivity.legsChecked) {
                    legsCheckBox3.setVisibility(View.INVISIBLE);
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);
                }
            }
        }


// ******************************* ADVANCED SET ***********************************************************************

        if (UserSetup.levelInputVar.equals("Advanced")) {
            maxPercentage.setText("80% and more of 1 rep max");

            if (UserSetup.goalInputVar.equals("Lose Weight")) {
                setDisplay.setText("Reps: 3x8");
                if (MainActivity.chestChecked) {
                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                    Random randomChest4 = new Random(System.currentTimeMillis());
                    int selectChest4 = randomChest4.nextInt(chestList.size());
                    chestCheckBox4.setText(selectChest4);
                }
                if (MainActivity.shoulderChecked) {
                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);

                    Random randomShoulder4 = new Random(System.currentTimeMillis());
                    int selectShoulder4 = randomShoulder4.nextInt(shoulderList.size());
                    shoulderCheckBox4.setText(selectShoulder4);
                }

                if (MainActivity.backChecked) {
                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);

                    Random randomBack4 = new Random(System.currentTimeMillis());
                    int selectBack4 = randomBack4.nextInt(backList.size());
                    backCheckBox4.setText(selectBack4);
                }
                if (MainActivity.armsChecked) {
                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                    Random randomArms4 = new Random(System.currentTimeMillis());
                    int selectArms4 = randomArms4.nextInt(armsList.size());
                    armsCheckBox4.setText(selectArms4);

                }
                if (MainActivity.legsChecked) {
                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);

                    Random randomLegs4 = new Random(System.currentTimeMillis());
                    int selectLegs4 = randomLegs4.nextInt(legList.size());
                    legsCheckBox4.setText(selectLegs4);
                }
            }
            if (UserSetup.goalInputVar.equals("Build Muscle")) {
                setDisplay.setText("Reps: 4x10");
                if (MainActivity.chestChecked) {
                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                    Random randomChest4 = new Random(System.currentTimeMillis());
                    int selectChest4 = randomChest4.nextInt(chestList.size());
                    chestCheckBox4.setText(selectChest4);
                }
                if (MainActivity.shoulderChecked) {
                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);

                    Random randomShoulder4 = new Random(System.currentTimeMillis());
                    int selectShoulder4 = randomShoulder4.nextInt(shoulderList.size());
                    shoulderCheckBox4.setText(selectShoulder4);
                }

                if (MainActivity.backChecked) {
                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);

                    Random randomBack4 = new Random(System.currentTimeMillis());
                    int selectBack4 = randomBack4.nextInt(backList.size());
                    backCheckBox4.setText(selectBack4);
                }
                if (MainActivity.armsChecked) {
                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                    Random randomArms4 = new Random(System.currentTimeMillis());
                    int selectArms4 = randomArms4.nextInt(armsList.size());
                    armsCheckBox4.setText(selectArms4);

                }
                if (MainActivity.legsChecked) {
                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);

                    Random randomLegs4 = new Random(System.currentTimeMillis());
                    int selectLegs4 = randomLegs4.nextInt(legList.size());
                    legsCheckBox4.setText(selectLegs4);
                }
            }

            if (UserSetup.goalInputVar.equals("Improve Definition")) {
                setDisplay.setText("Reps: 3x12");
                if (MainActivity.chestChecked) {
                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                    Random randomChest4 = new Random(System.currentTimeMillis());
                    int selectChest4 = randomChest4.nextInt(chestList.size());
                    chestCheckBox4.setText(selectChest4);
                }
                if (MainActivity.shoulderChecked) {
                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);

                    Random randomShoulder4 = new Random(System.currentTimeMillis());
                    int selectShoulder4 = randomShoulder4.nextInt(shoulderList.size());
                    shoulderCheckBox4.setText(selectShoulder4);
                }

                if (MainActivity.backChecked) {
                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);

                    Random randomBack4 = new Random(System.currentTimeMillis());
                    int selectBack4 = randomBack4.nextInt(backList.size());
                    backCheckBox4.setText(selectBack4);
                }
                if (MainActivity.armsChecked) {
                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);

                    Random randomArms4 = new Random(System.currentTimeMillis());
                    int selectArms4 = randomArms4.nextInt(armsList.size());
                    armsCheckBox4.setText(selectArms4);

                }
                if (MainActivity.legsChecked) {
                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);

                    Random randomLegs4 = new Random(System.currentTimeMillis());
                    int selectLegs4 = randomLegs4.nextInt(legList.size());
                    legsCheckBox4.setText(selectLegs4);
                }
            }

            if (UserSetup.goalInputVar.equals("Improve General Fitness")) {
                setDisplay.setText("Reps: 3x8");
                if (MainActivity.chestChecked) {
                    chestCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomChest1 = new Random(System.currentTimeMillis());
                    int selectChest1 = randomChest1.nextInt(chestList.size());
                    chestCheckBox1.setText(selectChest1);

                    Random randomChest2 = new Random(System.currentTimeMillis());
                    int selectChest2 = randomChest2.nextInt(chestList.size());
                    chestCheckBox2.setText(selectChest2);

                    Random randomChest3 = new Random(System.currentTimeMillis());
                    int selectChest3 = randomChest3.nextInt(chestList.size());
                    chestCheckBox3.setText(selectChest3);

                }
                if (MainActivity.shoulderChecked) {
                    shoulderCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomShoulder1 = new Random(System.currentTimeMillis());
                    int selectShoulder1 = randomShoulder1.nextInt(shoulderList.size());
                    shoulderCheckBox1.setText(selectShoulder1);

                    Random randomShoulder2 = new Random(System.currentTimeMillis());
                    int selectShoulder2 = randomShoulder2.nextInt(shoulderList.size());
                    shoulderCheckBox2.setText(selectShoulder2);

                    Random randomShoulder3 = new Random(System.currentTimeMillis());
                    int selectShoulder3 = randomShoulder3.nextInt(shoulderList.size());
                    shoulderCheckBox3.setText(selectShoulder3);

                }

                if (MainActivity.backChecked) {
                    backCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomBack1 = new Random(System.currentTimeMillis());
                    int selectBack1 = randomBack1.nextInt(backList.size());
                    backCheckBox1.setText(selectBack1);

                    Random randomBack2 = new Random(System.currentTimeMillis());
                    int selectBack2 = randomBack2.nextInt(backList.size());
                    backCheckBox2.setText(selectBack2);

                    Random randomBack3 = new Random(System.currentTimeMillis());
                    int selectBack3 = randomBack3.nextInt(backList.size());
                    backCheckBox3.setText(selectBack3);
                }
                if (MainActivity.armsChecked) {
                    armsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomArms1 = new Random(System.currentTimeMillis());
                    int selectArms1 = randomArms1.nextInt(armsList.size());
                    armsCheckBox1.setText(selectArms1);

                    Random randomArms2 = new Random(System.currentTimeMillis());
                    int selectArms2 = randomArms2.nextInt(armsList.size());
                    armsCheckBox2.setText(selectArms2);

                    Random randomArms3 = new Random(System.currentTimeMillis());
                    int selectArms3 = randomArms3.nextInt(armsList.size());
                    armsCheckBox3.setText(selectArms3);
                }
                if (MainActivity.legsChecked) {
                    legsCheckBox4.setVisibility(View.INVISIBLE);

                    Random randomLegs1 = new Random(System.currentTimeMillis());
                    int selectLegs1 = randomLegs1.nextInt(legList.size());
                    legsCheckBox1.setText(selectLegs1);

                    Random randomLegs2 = new Random(System.currentTimeMillis());
                    int selectLegs2 = randomLegs2.nextInt(legList.size());
                    legsCheckBox2.setText(selectLegs2);

                    Random randomLegs3 = new Random(System.currentTimeMillis());
                    int selectLegs3 = randomLegs3.nextInt(legList.size());
                    legsCheckBox3.setText(selectLegs3);
                }
            }
        }
    }
}
