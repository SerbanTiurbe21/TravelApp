package com.google.myapplication_test.database;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    public final String email;
    @NonNull
    public final String name;
    @NonNull
    public final String password;
    @Embedded
    public final City city;

    public User(@NonNull String email, @NonNull String name, @NonNull String password, @NonNull City city) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.city = city;
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

    public City getCity() {
        return city;
    }
}
