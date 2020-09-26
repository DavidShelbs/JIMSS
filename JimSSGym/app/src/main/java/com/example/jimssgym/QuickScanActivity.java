package com.example.jimssgym;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import pl.droidsonroids.gif.GifImageView;

public class QuickScanActivity extends AppCompatActivity {

    private TextView exerciseTitle;
    private TextView exerciseArea;

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
    }

    private void readData(String qr_result) {
        InputStream is = getResources().openRawResource(R.raw.exercises);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        exerciseArea = findViewById(R.id.exerciseArea);
        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");
                if (tokens[0].toLowerCase().contains(qr_result.toLowerCase())) {
                    exerciseArea.setText(tokens[1]);
                }
                Log.d("QuickScanActivity" ,"Just Created " + tokens);
            }
        } catch (IOException e1) {
            exerciseArea.setText("error");
            Log.e("QuickScanActivity", "Error" + line, e1);
            e1.printStackTrace();
        }
    }
}
