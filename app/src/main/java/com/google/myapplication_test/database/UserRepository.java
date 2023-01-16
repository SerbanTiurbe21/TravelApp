package com.google.myapplication_test.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAll();
    }

    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public LiveData<List<User>> getAllUsersByEmail(String email) {
        return mUserDao.findByEmail(email);
    }

    public int countUsersByEmail(String email) {
        return mUserDao.countUsersByEmail(email);
    }

    public int countUsersByUsername(String name){
        return mUserDao.countUsersByName(name);
    }

    void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }
}
