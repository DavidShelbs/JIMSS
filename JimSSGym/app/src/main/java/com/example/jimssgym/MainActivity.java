package com.example.jimssgym;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button quick_scan_button;
    private Button your_page_button;
    private Button workout_button;
    private Button friends_button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //ImageView cal = (ImageView)findViewById(R.id.calendar);
        //cal.bringToFront();

        quick_scan_button = findViewById(R.id.button2);
        your_page_button = findViewById(R.id.button3);
        workout_button = findViewById(R.id.button1);
        friends_button = findViewById(R.id.button4);


        quick_scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QRReaderActivity.class));
            }
        });
        your_page_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });
        workout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        friends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FriendsActivity.class));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            setTitle("Hello, " + currentUser.getDisplayName());
        }
        else {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }
}