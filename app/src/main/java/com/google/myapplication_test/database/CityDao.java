package com.google.myapplication_test.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CityDao {
    @Insert
    void insert(City city);

    @Update
    void update(City city);

    @Delete
    void delete(City city);

    @Query("SELECT * FROM cities")
    LiveData<List<City>> getAll();

    @Query("SELECT * FROM cities WHERE userId = :cityId")
    City findById(int cityId);

    @Query("SELECT * FROM cities c JOIN users u ON c.userId = u.userId WHERE u.userId = :userId")
    LiveData<List<City>> getAllCities(String userId);

    @Query("UPDATE cities SET tripName = :tripName, destination = :destination,price = :price, rating = :rating, photoUri = :uri WHERE userId = :id")
    void updateCity(String tripName, String destination,float price, float rating, String id, String uri);

    @Query("SELECT * FROM cities c JOIN users u ON c.userId = u.userId WHERE c.destination = :destination AND u.userId = :userId")
    City getCityByDestinationAndUserEmail(String destination, String userId);
}
