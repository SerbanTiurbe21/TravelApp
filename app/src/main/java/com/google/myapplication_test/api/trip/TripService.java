package com.google.myapplication_test.api.trip;

import com.google.myapplication_test.entities.TripDB;
import com.google.myapplication_test.fragments.trip.Trip;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TripService {
    @POST("api/trips")
    Call<TripDB> saveTrip(@Body TripDB trip);
}
