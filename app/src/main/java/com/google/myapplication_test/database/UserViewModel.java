package com.google.myapplication_test.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    LiveData<List<User>> users;
    private MutableLiveData<Integer> usernameCount = new MutableLiveData<>();
    private MutableLiveData<Integer> emailCount = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getAllUsers();
    }

    public void insert(User user){
        userRepository.insert(user);
    }

    public LiveData<List<User>> getUsersByEmail(String email){
        return userRepository.getAllUsersByEmail(email);
    }

    /*
    public LiveData<Integer> countUsersByEmail(String email) {
        emailCount.postValue(userRepository.countUsersByEmail(email));
        return emailCount;
    }*/

    public LiveData<Integer> countUsersByUsername(String username) {
        usernameCount.postValue(userRepository.countUsersByUsername(username));
        return usernameCount;
    }

    LiveData<List<User>> getUsers(){
        return users;
    }

    public LiveData<Integer> countUsersByEmail(final String email) {
        final MutableLiveData<Integer> emailCount = new MutableLiveData<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                emailCount.postValue(userRepository.countUsersByEmail(email));
            }
        });
        thread.start();
        return emailCount;
    }
}
