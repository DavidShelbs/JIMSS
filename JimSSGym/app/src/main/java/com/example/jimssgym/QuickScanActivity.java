package com.example.jimssgym;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import java.util.Arrays;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class QuickScanActivity extends AppCompatActivity {

    private TextView exerciseTitle;
    private TextView exerciseArea;
    public TextView exerciseDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickscan);
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        String qr_result = getIntent().getStringExtra("QR_RESULT");

        exerciseTitle = findViewById(R.id.exerciseTitle);
        exerciseTitle.setText(qr_result.toUpperCase());

        readData(qr_result);

        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.earth);
        int height = bitmapOrg.getHeight();
        int width = bitmapOrg.getWidth();
        GifImageView gifView = findViewById(R.id.gifView);
        ViewGroup.LayoutParams params = gifView.getLayoutParams();
        params.height = height;
        params.width = width;
        gifView.setLayoutParams(params);
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

    private void readData(String qr_result) {
//        InputStream is_csv = getResources().openRawResource(R.raw.exercises_csv);
//        BufferedReader reader_csv = new BufferedReader(new InputStreamReader(is_csv, Charset.forName("UTF-8")));
//        String line_csv = "";
        exerciseArea = findViewById(R.id.exerciseArea);
        exerciseDescription = findViewById(R.id.exerciseDescription);
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
        InputStream is_json = getResources().openRawResource(R.raw.exercises_json);
        BufferedReader reader_json = new BufferedReader(new InputStreamReader(is_json, Charset.forName("UTF-8")));
        String lines_json = "";
        try {
            while ((lines_json = reader_json.readLine()) != null) {
                JSONArray jsonArray = new JSONArray(lines_json);
                for (int i=0; i < jsonArray.length(); i++)
                {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String item = jsonObject.getString("name");
                        if (item.equals(qr_result)) {
                            String muscle_string = jsonObject.getString("muscles");
                            muscle_string = muscle_string.substring(1, muscle_string.length() - 1);
                            exerciseDescription.setText(jsonObject.getString("description").replaceAll("<[^>]*>", ""));
                            String muscles[] = muscle_string.split(",");
                            muscle_string = "";
                            for (int j=0; j < muscles.length; j++) {
                                if (!muscle_string.equals("")) {
                                    muscle_string += ", ";
                                }
                                switch (muscles[j])
                                {
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
                        else {
                            exerciseArea.setText("Error");
                            exerciseDescription.setText("Equipment Not Recognised");
                        }
                    } catch (JSONException e) {
                        // Oops
                    }
                }

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
