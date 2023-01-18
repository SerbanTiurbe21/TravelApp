package com.google.myapplication_test.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Insert
    void insertCity(User user, City city);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<List<User>> findByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email and password = :password")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email")
    User email(String email);

    @Query("SELECT * FROM users WHERE name = :name")
    LiveData<List<User>> findByName(String name);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email AND password = :password")
    int findByEmailAndPassword(String email, String password);

    @Query("SELECT COUNT(*) FROM users WHERE name = :name")
    int countUsersByName(String name);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int countUsersByEmail(String email);

    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    void updatePassword(String email, String newPassword);

    @Query("SELECT * FROM cities c JOIN users u ON c.userId = u.userId WHERE u.userId = :userId")
    List<City> getCitiesForUser(String userId);

}
