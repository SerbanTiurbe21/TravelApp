package com.google.myapplication_test.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CityRepository {
    private CityDao mCityDao;
    private LiveData<List<City>> mAllCities;

    CityRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mCityDao = db.cityDao();
        mAllCities = mCityDao.getAll();
    }

    LiveData<List<City>> getAllCities() {
        return mAllCities;
    }

    void insert(City city){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCityDao.insert(city);
        });
    }
}
