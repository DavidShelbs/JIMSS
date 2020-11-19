package com.example.jimssgym;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;

public class FriendCardViewActivity extends Activity {

//    private TextView popUp;
    RecyclerView recyclerView;
    FriendCardViewAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);

//        String clickedType = getIntent().getStringExtra("POPTYPE");

        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() != 0) {
                    getData(query, new DataListener() {
                        @Override
                        public void newDataReceived(ArrayList<FriendCardViewModel> models) {
                            adapter = new FriendCardViewAdapter(FriendCardViewActivity.this, models);
                            recyclerView.setAdapter(adapter);
//                    ((RecyclerView.Adapter) recyclerView.getAdapter()).notifyDataSetChanged();
                        }
                    });
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        getWindow().setLayout((int)(width*.8), (int)(height*.6));

//        popUp = findViewById(R.id.type);
//        popUp.setText(clickedType);

//        // Submit button
//        Button button = findViewById(R.id.delete_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(PopUpDelete.this, "Deleted Event", Toast.LENGTH_SHORT).show();
//            }
//        });



    }

    public interface DataListener {
        void newDataReceived(ArrayList<FriendCardViewModel> models);
    }

    ArrayList<FriendCardViewModel> getData(final String query, final FriendCardViewActivity.DataListener dataListener) {
        final ArrayList<FriendCardViewModel> models = new ArrayList<>();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String current_user_id = mAuth.getUid();

        db.collection("users/").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getData());
                                FriendCardViewModel m = new FriendCardViewModel();
                                if (document.getData().get("name").toString().toLowerCase().contains((query.toLowerCase()))) {
                                    int index = document.getData().get("name").toString().indexOf(' ');
                                    m.setFName(document.getData().get("name").toString().substring(0, index));
                                    m.setLName(document.getData().get("name").toString().substring(index + 1));
                                    models.add(m);
                                }
                            }
                            dataListener.newDataReceived(models);
                        }
                        else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });
        return models;
    }

}




