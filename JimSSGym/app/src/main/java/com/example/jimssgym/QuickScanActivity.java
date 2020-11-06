package com.example.jimssgym;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class QuickScanActivity extends AppCompatActivity {

    private TextView exerciseTitle;
    private TextView exerciseArea;
    public TextView exerciseDescription;
    private ImageView exerciseImage;
    private Handler handler;
    private Button workout_button;
    private String exercise_id;
    private NumberPicker picker;
    private String[] days_of_week = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickscan);
        isStoragePermissionGranted();
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        String qr_result = getIntent().getStringExtra("QR_RESULT");
        exerciseImage = findViewById(R.id.imageView);
        workout_button = findViewById(R.id.add_workout_button);
        exerciseTitle = findViewById(R.id.exerciseTitle);
        exerciseTitle.setText(qr_result.toUpperCase());

        picker = findViewById(R.id.dow);
        picker.setMinValue(0);
        picker.setMaxValue(6);
        picker.setDisplayedValues(days_of_week);

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
                } catch (JSONException | IOException | InterruptedException e) {
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

    public  boolean isStoragePermissionGranted() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
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

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("workouts.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void add_workout(final String exercise_id) throws JSONException, IOException, InterruptedException {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String current_user_id = mAuth.getUid();
//        String user_fname = mAuth.getCurrentUser().getDisplayName();

        // Create a new user with a first and last name
        Map<String, Object> user_workout = new HashMap<>();
        user_workout.put("reps", 1);
        user_workout.put("sets", 1);
        String dow = days_of_week[picker.getValue()].toLowerCase();

        // Add a new document with a generated ID
        db.collection("users").document(current_user_id + "/workouts/" + dow + "/exercises/" + exerciseTitle.getText())
                .set(user_workout)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added with ID: " + current_user_id);
                        Toast.makeText(QuickScanActivity.this, "Exercise Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document" + e);
                    }
                });


        db.collection("users/" + current_user_id + "/workouts/" + dow + "/exercises")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println(document.getId() + " => " + document.getData());
                        }
                    } else {
                        System.out.println("Error getting documents: " + task.getException());
                    }
                }
            });


//        FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-app-1039b.appspot.com/");
//        StorageReference gsReference = storage.getReferenceFromUrl("gs://fir-app-1039b.appspot.com/workouts.txt");
//        File rootPath = new File(getFilesDir(), "");
//        if(!rootPath.exists()) {
//            rootPath.mkdirs();
//        }
//        final File localFile = new File(rootPath,"workouts.txt");
//
//        gsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                String current_user_id = mAuth.getUid();
//                Context context = getApplicationContext();
//                String current_user_data = "";
//                current_user_data = readFromFile(context);
//                System.out.println(current_user_data);
//
//                JSONObject workoutObject = new JSONObject();
//                JSONObject exerciseObject= new JSONObject();
//                JSONArray exerciseArray= new JSONArray();
//                JSONArray workoutArray = null;
//
//                if (current_user_data.equals("")) {
//                    workoutArray = new JSONArray();
//                }
//                else {
//                    try {
//                        workoutArray = new JSONArray(current_user_data);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    for (int l = 0; l < workoutArray.length(); l++) {
//                        try {
//                            if (workoutArray.getJSONObject(l).getString("userid").equals(current_user_id)) {
//                                workoutObject = workoutArray.getJSONObject(l);
//                                exerciseArray = workoutObject.getJSONArray("exercises");
//                                exerciseObject.put("id", exercise_id);
//                                exerciseObject.put("reps", 1);
//                                exerciseObject.put("sets", 1);
//                                exerciseArray.put(exerciseObject);
//                                workoutObject.put("exercises", exerciseArray);
//                                writeToFile(workoutArray.toString(), context);
//                                return;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                try {
//                    exerciseObject.put("id", exercise_id);
//                    exerciseObject.put("reps", 1);
//                    exerciseObject.put("sets", 1);
//                    exerciseArray.put(exerciseObject);
//                    workoutObject.put("userid", current_user_id);
//                    workoutObject.put("dayofweek", "Sunday");
//                    workoutObject.put("exercises", exerciseArray);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                workoutArray.put(workoutObject);
//
//                System.out.println(workoutArray.toString());
//
//                writeToFile(workoutArray.toString(), context);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                System.out.println("firebase;local tem file not created  created " +exception.toString());
//            }
//        });
//
//        sleep(1000);
//        Uri file = Uri.fromFile(new File(getApplicationContext().getFilesDir() + "/workouts.txt"));
//        gsReference = storage.getReferenceFromUrl("gs://fir-app-1039b.appspot.com/workouts.txt");
//        Task up_tsk = gsReference.putFile(file);
//        up_tsk.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                taskSnapshot.getMetadata();
//                System.out.println("firebase;local tem file created ");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                System.out.println("firebase;local tem file not created  created " +exception.toString());
//            }
//        });
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