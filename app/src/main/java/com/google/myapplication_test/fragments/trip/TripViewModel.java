package com.google.myapplication_test.fragments.trip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TripViewModel extends ViewModel {

    private MutableLiveData<List<Trip>> dataSource = new MutableLiveData<>();

    public void setDataSource(List<Trip> dataSource) {
        this.dataSource.setValue(dataSource);
    }

    public LiveData<List<Trip>> getDataSource() {
        return dataSource;
    }
}
