package com.google.myapplication_test.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "userId")
    String userId;

    @ColumnInfo(name = "email")
    @NonNull
    public final String email;

    @ColumnInfo(name = "name")
    @NonNull
    public final String name;

    @ColumnInfo(name = "password")
    @NonNull
    public final String password;


    public User(@NonNull String email, @NonNull String name, @NonNull String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }


    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
