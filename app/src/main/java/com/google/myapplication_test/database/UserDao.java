package com.google.myapplication_test.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM users WHERE email = :email")
    User findByEmail(String email);

    @Query("SELECT * FROM users WHERE name = :name")
    User findByName(String name);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email AND password = :password")
    int findByEmailAndPassword(String email, String password);

    @Query("SELECT COUNT(*) FROM users WHERE name = :name")
    int countUsersByName(String name);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int countUsersByEmail(String email);
}
