package com.example.jimssgym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class QRReaderQuickScanActivity extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrreader);
        scanView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner( this, scanView);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                Intent intent = new Intent(getBaseContext(), QuickScanCardViewActivity.class);
                intent.putExtra("QR_RESULT", result.getText());
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings:
                        Intent intent = new Intent(QRReaderQuickScanActivity.this, UserActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.workout:
                        Intent intent2 = new Intent(QRReaderQuickScanActivity.this, ScanCardViewActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.home:
                        Intent intent3 = new Intent(QRReaderQuickScanActivity.this, MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.friends:
                        Intent intent4 = new Intent(QRReaderQuickScanActivity.this, FriendsActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(QRReaderQuickScanActivity.this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }


}
