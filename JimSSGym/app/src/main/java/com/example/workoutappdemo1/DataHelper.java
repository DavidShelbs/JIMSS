package com.example.workoutappdemo1;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DataHelper {

    public static ArrayList<Workout> loadWorkout(Context context){
        ArrayList<Workout> workouts = new ArrayList<>();
        String json = "";

        try{
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("friends");

            for (int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Workout workout = new Workout();

                workout.setName(jsonObject.getString("name"));
                workout.setMonday(jsonObject.getString("monday"));
                workout.setTuesday(jsonObject.getString("tuesday"));
                workout.setWednesday(jsonObject.getString("wednesday"));
                workout.setThursday(jsonObject.getString("thursday"));
                workout.setFriday(jsonObject.getString("friday"));
                workout.setSaturday(jsonObject.getString("saturday"));
                workout.setSunday(jsonObject.getString("sunday"));

                workouts.add(workout);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return workouts;
    }
}
