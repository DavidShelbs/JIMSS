package com.example.jimssgym;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ScanActivity extends AppCompatActivity {

    private TextView exerciseTitle;
    private TextView exerciseArea;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        String qr_result = getIntent().getStringExtra("QR_RESULT");

        exerciseTitle = findViewById(R.id.exerciseTitle);
        exerciseTitle.setText(qr_result.toUpperCase());

        try {
            readData(qr_result);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private void readData(String qr_result) throws IOException {
//        InputStream is_csv = getResources().openRawResource(R.raw.exercises_csv);
//        BufferedReader reader_csv = new BufferedReader(new InputStreamReader(is_csv, Charset.forName("UTF-8")));
//        String line_csv = "";
        exerciseArea = findViewById(R.id.exerciseArea);
        AnimationDrawable animation;


        InputStream exercises_json = getResources().openRawResource(R.raw.exercises);
        BufferedReader exercises_reader = new BufferedReader(new InputStreamReader(exercises_json, Charset.forName("UTF-8")));
        String exercises_lines = "";
        try {
            while ((exercises_lines = exercises_reader.readLine()) != null) {
                JSONArray exercisesArray = new JSONArray(exercises_lines);
                for (int j = 0; j < exercisesArray.length(); j++) {
                    try {
                        JSONObject exerciseObject = exercisesArray.getJSONObject(j);
                        String exercise_id = exerciseObject.getString("id");
                        String exercise_name = exerciseObject.getString("name");
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

                                                animation = new AnimationDrawable();
                                                animation.addFrame(getResources().getDrawable(id), 500);
                                                animation.addFrame(getResources().getDrawable(id2), 500);
                                                animation.setOneShot(false);
                                                animation.start();


                                                break;
                                            } else {
                                                int id = getApplicationContext().getResources().getIdentifier("no_image_available", "drawable", getApplicationContext().getPackageName());
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


//        try {
//            while ((line_csv = reader_csv.readLine()) != null) {
//                String[] tokens = line_csv.split(",");
//                if (tokens[0].toLowerCase().contains(qr_result.toLowerCase())) {
//                    exerciseArea.setText(tokens[1]);
//                }
//                Log.d("QuickScanActivity" ,"Just Created " + tokens);
//            }
//        } catch (IOException e1) {
//            exerciseArea.setText("error");
//            Log.e("QuickScanActivity", "Error" + line_csv, e1);
//            e1.printStackTrace();
//        }
//        String[] urls = new String[]{"https://wger.de/api/v2/exercise/?format=json&language=2&status=2&limit=199&name=bench"};
//        new GetUrlContentTask(this).execute(urls);


//        InputStream is_json = getResources().openRawResource(R.raw.exercises_json);
//        BufferedReader reader_json = new BufferedReader(new InputStreamReader(is_json, Charset.forName("UTF-8")));
//        String lines_json = "";
//        try {
//            while ((lines_json = reader_json.readLine()) != null) {
//                JSONArray jsonArray = new JSONArray(lines_json);
//                for (int i=0; i < jsonArray.length(); i++)
//                {
//                    try {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String item = jsonObject.getString("name");
//                        if (item.equals(qr_result)) {
//                            String muscle_string = jsonObject.getString("muscles");
//                            muscle_string = muscle_string.substring(1, muscle_string.length() - 1);
//                            exerciseDescription.setText(jsonObject.getString("description").replaceAll("<[^>]*>", ""));
//                            String muscles[] = muscle_string.split(",");
//                            muscle_string = "";
//                            for (int j=0; j < muscles.length; j++) {
//                                if (!muscle_string.equals("")) {
//                                    muscle_string += ", ";
//                                }
//                                switch (muscles[j])
//                                {
//                                    case "1":
//                                        muscle_string += "Biceps brachii";
//                                        break;
//                                    case "2":
//                                        muscle_string += "Anterior deltoid";
//                                        break;
//                                    case "3":
//                                        muscle_string += "Serratus anterior";
//                                        break;
//                                    case "4":
//                                        muscle_string += "Pectoralis major";
//                                        break;
//                                    case "5":
//                                        muscle_string += "Triceps brachii";
//                                        break;
//                                    case "6":
//                                        muscle_string += "Rectus abdominis";
//                                        break;
//                                    case "7":
//                                        muscle_string += "Gastrocnemius";
//                                        break;
//                                    case "8":
//                                        muscle_string += "Gluteus maximus";
//                                        break;
//                                    case "9":
//                                        muscle_string += "Trapezius";
//                                        break;
//                                    case "10":
//                                        muscle_string += "Quadriceps femoris";
//                                        break;
//                                    case "11":
//                                        muscle_string += "Biceps femoris";
//                                        break;
//                                    case "12":
//                                        muscle_string += "Latissimus dorsi";
//                                        break;
//                                    case "13":
//                                        muscle_string += "Brachialis";
//                                        break;
//                                    case "14":
//                                        muscle_string += "Obliquus externus abdominis";
//                                        break;
//                                    case "15":
//                                        muscle_string += "Soleus";
//                                        break;
//                                }
//                            }
//                            exerciseArea.setText(muscle_string);
//                            break;
//                        }
//                        else {
//                            exerciseArea.setText("Error");
//                            exerciseDescription.setText("Equipment Not Recognised");
//                        }
//                    } catch (JSONException e) {
//                        // Oops
//                    }
//                }
//
//            }
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
