package com.example.workoutappdemo1;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DataHelper {

    public static ArrayList<ScheduleModel> loadWorkout(Context context){
        ArrayList<ScheduleModel> scheduleModels = new ArrayList<>();
        String json = "";

        try{
            InputStream is = context.getAssets().open("dataFriends.json");
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
                ScheduleModel scheduleModel = new ScheduleModel();

                scheduleModel.setName(jsonObject.getString("name"));
                scheduleModel.setMonday(jsonObject.getString("monday"));
                scheduleModel.setTuesday(jsonObject.getString("tuesday"));
                scheduleModel.setWednesday(jsonObject.getString("wednesday"));
                scheduleModel.setThursday(jsonObject.getString("thursday"));
                scheduleModel.setFriday(jsonObject.getString("friday"));
                scheduleModel.setSaturday(jsonObject.getString("saturday"));
                scheduleModel.setSunday(jsonObject.getString("sunday"));

                scheduleModels.add(scheduleModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return scheduleModels;
    }
}
