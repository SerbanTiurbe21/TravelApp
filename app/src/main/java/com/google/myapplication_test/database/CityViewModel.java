package com.google.myapplication_test.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CityViewModel extends AndroidViewModel {

    private CityRepository cityRepository;
    LiveData<List<City>> cities;

    public CityViewModel(@NonNull Application application) {
        super(application);
        cityRepository = new CityRepository(application);
        cities = cityRepository.getAllCities();
    }

    void insert(City city){
        cityRepository.insert(city);
    }

    LiveData<List<City>> getCities(){
        return cities;
    }
}
