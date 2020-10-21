package com.example.jimssgym;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class QuickScanActivity extends AppCompatActivity {

    private TextView exerciseTitle;
    private TextView exerciseArea;
    public TextView exerciseDescription;
    private ImageView exerciseImage;
    private Handler handler;
    private Button workout_button;
    private String exercise_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickscan);
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        String qr_result = getIntent().getStringExtra("QR_RESULT");
        exerciseImage = findViewById(R.id.imageView);
        workout_button = findViewById(R.id.add_workout_button);
        exerciseTitle = findViewById(R.id.exerciseTitle);
        exerciseTitle.setText(qr_result.toUpperCase());

        try {
            exercise_id = readData(qr_result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        workout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add_workout(exercise_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.earth);
        int height = bitmapOrg.getHeight();
        int width = bitmapOrg.getWidth();

        Window window = this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
            this.finish();
        }
        return super.dispatchTouchEvent(ev);
    }

    private String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("workout.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("workout.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void add_workout(String exercise_id) throws JSONException {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String current_user_id = mAuth.getUid();
        Context context = getApplicationContext();
        String current_user_data = "";
        current_user_data = readFromFile(context);
        System.out.println(current_user_data);

        JSONObject workoutObject = new JSONObject();
        JSONObject exerciseObject= new JSONObject();
        JSONArray exerciseArray= new JSONArray();
        JSONArray workoutArray;

        if (current_user_data.equals("")) {
            workoutArray = new JSONArray();
        }
        else {
            workoutArray = new JSONArray(current_user_data);
        }

        exerciseObject.put("id", exercise_id);
        exerciseObject.put("reps", 1);
        exerciseObject.put("sets", 1);

        exerciseArray.put(exerciseObject);

        workoutObject.put("userid", current_user_id);
        workoutObject.put("dayofweek", "Sunday");
        workoutObject.put("exercises", exerciseArray);

        workoutArray.put(workoutObject);

        writeToFile(workoutArray.toString(), context);
    }

    private String readData(String qr_result) throws IOException {
        exerciseArea = findViewById(R.id.exerciseArea);
        exerciseDescription = findViewById(R.id.exerciseDescription);
        exerciseImage = findViewById(R.id.imageView);
        AnimationDrawable animation;
        String exercise_id = null;

        InputStream exercises_json = getResources().openRawResource(R.raw.exercises);
        BufferedReader exercises_reader = new BufferedReader(new InputStreamReader(exercises_json, Charset.forName("UTF-8")));
        String exercises_lines = "";
        try {
            while ((exercises_lines = exercises_reader.readLine()) != null) {
                JSONArray exercisesArray = new JSONArray(exercises_lines);
                for (int j = 0; j < exercisesArray.length(); j++) {
                    try {
                        JSONObject exerciseObject = exercisesArray.getJSONObject(j);
                        exercise_id = exerciseObject.getString("id");
                        String exercise_name = exerciseObject.getString("name");
                        exerciseDescription.setText(exerciseObject.getString("description").replaceAll("<[^>]*>", ""));
                        if (exercise_name.toLowerCase().equals(qr_result.toLowerCase())) {
                            InputStream images_json = getResources().openRawResource(R.raw.images);
                            BufferedReader images_reader = new BufferedReader(new InputStreamReader(images_json, Charset.forName("UTF-8")));
                            String images_lines = "";
                            try {
                                while ((images_lines = images_reader.readLine()) != null) {
                                    JSONArray imagesArray = new JSONArray(images_lines);
                                    for (int l = 0; l < imagesArray.length(); l++) {
                                        try {
                                            JSONObject imagesObject = imagesArray.getJSONObject(l);
                                            String images_exercise = imagesObject.getString("exercise");
                                            if (images_exercise.toLowerCase().equals(exercise_id.toLowerCase())) {
                                                String image_id = imagesObject.getString("id");
                                                String image_name = "exercise_image_" + image_id;
                                                int id = getApplicationContext().getResources().getIdentifier(image_name, "drawable", getApplicationContext().getPackageName());
                                                JSONObject nextObject = imagesArray.getJSONObject(l+1);
                                                image_id = nextObject.getString("id");
                                                image_name = "exercise_image_" + image_id;
                                                int id2 = getApplicationContext().getResources().getIdentifier(image_name, "drawable", getApplicationContext().getPackageName());
                                                exerciseImage.setImageResource(id);

                                                animation = new AnimationDrawable();
                                                animation.addFrame(getResources().getDrawable(id), 500);
                                                animation.addFrame(getResources().getDrawable(id2), 500);
                                                animation.setOneShot(false);
                                                exerciseImage.setImageDrawable(animation);
                                                animation.start();


                                                break;
                                            } else {
                                                int id = getApplicationContext().getResources().getIdentifier("no_image_available", "drawable", getApplicationContext().getPackageName());
                                                exerciseImage.setImageResource(id);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String muscle_string = exerciseObject.getString("muscles");
                            muscle_string = muscle_string.substring(1, muscle_string.length() - 1);
                            String muscles[] = muscle_string.split(",");
                            muscle_string = "";
                            for (int k = 0; k < muscles.length; k++) {
                                if (!muscle_string.equals("")) {
                                    muscle_string += ", ";
                                }
                                switch (muscles[k]) {
                                    case "1":
                                        muscle_string += "Biceps brachii";
                                        break;
                                    case "2":
                                        muscle_string += "Anterior deltoid";
                                        break;
                                    case "3":
                                        muscle_string += "Serratus anterior";
                                        break;
                                    case "4":
                                        muscle_string += "Pectoralis major";
                                        break;
                                    case "5":
                                        muscle_string += "Triceps brachii";
                                        break;
                                    case "6":
                                        muscle_string += "Rectus abdominis";
                                        break;
                                    case "7":
                                        muscle_string += "Gastrocnemius";
                                        break;
                                    case "8":
                                        muscle_string += "Gluteus maximus";
                                        break;
                                    case "9":
                                        muscle_string += "Trapezius";
                                        break;
                                    case "10":
                                        muscle_string += "Quadriceps femoris";
                                        break;
                                    case "11":
                                        muscle_string += "Biceps femoris";
                                        break;
                                    case "12":
                                        muscle_string += "Latissimus dorsi";
                                        break;
                                    case "13":
                                        muscle_string += "Brachialis";
                                        break;
                                    case "14":
                                        muscle_string += "Obliquus externus abdominis";
                                        break;
                                    case "15":
                                        muscle_string += "Soleus";
                                        break;
                                }
                            }
                            exerciseArea.setText(muscle_string);
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exercise_id;
    }
}