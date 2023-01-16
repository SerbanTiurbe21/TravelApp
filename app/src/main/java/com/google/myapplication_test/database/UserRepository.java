package com.google.myapplication_test.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;
    private LiveData<List<User>> allUsersByEmail;
    private MutableLiveData<Integer> countLiveData;

    UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAll();
        countLiveData = new MutableLiveData<>();
    }

    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public LiveData<List<User>> getAllUsersByEmail(String email) {
        return mUserDao.findByEmail(email);
    }

    public MutableLiveData<Integer> countUsersByName(String name) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            int count = mUserDao.countUsersByName(name);
            countLiveData.postValue(count);
        });
        return countLiveData;
    }

    public int countUsersByEmail(String email) {
        return mUserDao.countUsersByEmail(email);
    }

    void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }
}
