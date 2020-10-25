package com.example.jimssgym;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;


public class ScanCardViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ScanCardViewAdapter adapter;
    List<String> exercise_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_cardview);
        String qr_result = getIntent().getStringExtra("QR_RESULT");

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-app-1039b.appspot.com/");
        StorageReference gsReference = storage.getReferenceFromUrl("gs://fir-app-1039b.appspot.com/workouts.txt");
        File rootPath = new File(getFilesDir(), "");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }
        final File localFile = new File(rootPath,"workouts.txt");

        try {
            getMyList(qr_result, new DataListener() {
                @Override
                public void newDataReceived(ArrayList<ScanCardViewModel> models) {
                    adapter = new ScanCardViewAdapter(ScanCardViewActivity.this, models);
                    recyclerView.setAdapter(adapter);
//                    ((RecyclerView.Adapter) recyclerView.getAdapter()).notifyDataSetChanged();
                }
            });
        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("workouts.txt");
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

    public interface DataListener {
        void newDataReceived(ArrayList<ScanCardViewModel> models);
    }

    public ArrayList<ScanCardViewModel> getMyList(final String qr_result, final DataListener dataListener) throws IOException, JSONException, InterruptedException {

        final ArrayList<ScanCardViewModel> models = new ArrayList<>();

        Context context = getApplicationContext();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String current_user_id = mAuth.getUid();
        LocalDate date = LocalDate.now();
        DayOfWeek dow = date.getDayOfWeek();

        CollectionReference collectionReference = db.collection("users/" + current_user_id + "/workouts/" + dow.toString().toLowerCase() + "/exercises");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                                exercise_array.add(document.getId());
                            }

                            InputStream machines_json = getResources().openRawResource(R.raw.equipment);
                            BufferedReader machines_reader = new BufferedReader(new InputStreamReader(machines_json, Charset.forName("UTF-8")));
                            String machines_lines = "";
                            try {
                                while ((machines_lines = machines_reader.readLine()) != null) {
                                    JSONArray machinesArray = new JSONArray(machines_lines);
                                    for (int i = 0; i < machinesArray.length(); i++) {
                                        try {
                                            JSONObject machinesObject = machinesArray.getJSONObject(i);
                                            String equipment_name = machinesObject.getString("name");
                                            String equipment_id = machinesObject.getString("id");
                                            if (equipment_name.toLowerCase().equals(qr_result.toLowerCase())) {
                                                InputStream exercises_json = getResources().openRawResource(R.raw.exercises);
                                                BufferedReader exercises_reader = new BufferedReader(new InputStreamReader(exercises_json, Charset.forName("UTF-8")));
                                                String exercises_lines = "";
                                                try {
                                                    while ((exercises_lines = exercises_reader.readLine()) != null) {
                                                        JSONArray exercisesArray = new JSONArray(exercises_lines);
                                                        for (int j = 0; j < exercisesArray.length(); j++) {
                                                            try {
                                                                ScanCardViewModel m = new ScanCardViewModel();
                                                                JSONObject exerciseObject = exercisesArray.getJSONObject(j);
                                                                String equipment_string = exerciseObject.getString("equipment");
                                                                String exercise_name = exerciseObject.getString("name");
                                                                String exercise_id = exerciseObject.getString("id");

//                                            InputStream images_json = getResources().openRawResource(R.raw.images);
//                                            BufferedReader images_reader = new BufferedReader(new InputStreamReader(images_json, Charset.forName("UTF-8")));
//                                            String images_lines = "";
//                                            try {
//                                                while ((images_lines = images_reader.readLine()) != null) {
//                                                    JSONArray imagesArray = new JSONArray(images_lines);
//                                                    for (int l = 0; l < imagesArray.length(); l++) {
//                                                        try {
//                                                            JSONObject imagesObject = imagesArray.getJSONObject(l);
//                                                            String images_exercise = imagesObject.getString("exercise");
//                                                            if (images_exercise.toLowerCase().equals(exercise_id.toLowerCase())) {
//                                                                String image_id = imagesObject.getString("id");
//                                                                String image_name = "exercise_image_" + image_id;
//                                                                int id = getApplicationContext().getResources().getIdentifier(image_name, "drawable", getApplicationContext().getPackageName());
//                                                                m.setImg(id);
//                                                                break;
//                                                            }
//                                                        } catch (JSONException e) {
//                                                            e.printStackTrace();
//                                                        }
//                                                    }
//                                                }
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }

                                                                String muscle_string = exerciseObject.getString("muscles");
                                                                muscle_string = muscle_string.substring(1, muscle_string.length() - 1);
                                                                String muscles[] = muscle_string.split(",");
                                                                muscle_string = "";
                                                                for (int k=0; k < muscles.length; k++) {
                                                                    if (!muscle_string.equals("")) {
                                                                        muscle_string += ", ";
                                                                    }
                                                                    switch (muscles[k])
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
                                                                equipment_string = equipment_string.substring(1, equipment_string.length() - 1);
                                                                String equipment_array[] = equipment_string.split(",");
                                                                List<String> equipment = Arrays.asList(equipment_array);
//                                                                System.out.println(exercise_name);
//                                                                System.out.println(exercise_array.contains(exercise_name.toUpperCase()));
//                                                                System.out.println(equipment.contains(equipment_id));
                                                                if (equipment.contains(equipment_id) && exercise_array.contains(exercise_name.toUpperCase())) {
                                                                    m.setTitle(exercise_name);
                                                                    m.setDescription(muscle_string);
                                                                    models.add(m);
                                                                }

                                                            } catch (JSONException ex) {
                                                                ex.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
//                            System.out.println(models.size());
                            dataListener.newDataReceived(models);
                        }
                        else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });


//        String current_user_data = readFromFile(context);
//        JSONArray workoutArray = new JSONArray(current_user_data);
//        System.out.println(current_user_data);
//        for (int l = 0; l < workoutArray.length(); l++) {
//            try {
//                JSONObject workoutObject = workoutArray.getJSONObject(l);
//                String user_id = workoutObject.getString("userid");
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                String current_user_id = mAuth.getUid();
//                if (current_user_id.equals(user_id)) {
//                    JSONArray user_workoutArray = workoutObject.getJSONArray("exercises");
//                    for (int m = 0; m < user_workoutArray.length(); m++) {
//                        try {
//                            JSONObject user_workoutObject = user_workoutArray.getJSONObject(m);
//                            exercise_array.add(user_workoutObject.getString("id"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        return models;
    }
}
