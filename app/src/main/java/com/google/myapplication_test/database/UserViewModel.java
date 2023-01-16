package com.google.myapplication_test.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    LiveData<List<User>> users;

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

    public Boolean checkUsersEmail(String email){
        int count = userRepository.countUsersByEmail(email);
        if(count > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkUsersName(String name){
        int count = userRepository.countUsersByName(name);
        if(count > 0){
            return true;
        }
        else{
            return false;
        }
    }

    LiveData<List<User>> getUsers(){
        return users;
    }
}
