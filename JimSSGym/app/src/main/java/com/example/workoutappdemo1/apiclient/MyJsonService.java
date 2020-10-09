package com.example.workoutappdemo1.apiclient;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Callback;

/**
 * Created by Raquib-ul-Alam Kanak on 1/3/16.
 * Website: http://alamkanak.github.io
 */
public interface MyJsonService {

    @GET("/1kpjf")
    void listEvents(Callback<List<Event>> eventsCallback);

}
