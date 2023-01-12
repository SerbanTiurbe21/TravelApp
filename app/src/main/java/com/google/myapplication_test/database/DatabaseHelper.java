package com.google.myapplication_test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";

    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDatabase) {
        myDatabase.execSQL("create Table users(email TEXT primary key, username TEXT, password TEXT)");
        myDatabase.execSQL("CREATE TABLE Cities (_id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, name TEXT, arrived_on TEXT, left_on TEXT, " +
                "temperature REAL, latitude REAL, longitude REAL, image BLOB, FOREIGN KEY(email) REFERENCES users(email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDatabase, int i, int i1) {
        myDatabase.execSQL("drop Table if exists users");
    }

    // password to be encrypted
    public boolean insertData(String email, String username, String password) {
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password",password);
        long result = myDatabase.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkPassword(String email, String username, String password) {
        //String passwordEncrypted = encodePassword(email, password);
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from users where email = ? and username = ? and password = ?", new String[]{email, username, password});
        return cursor.getCount() > 0;
    }

    public Boolean checkPasswordForLogin(String email, String password) {
        //String passwordEncrypted = encodePassword(email, password);
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    public Cursor getUsername(String email){
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        Cursor cursor = myDatabase.rawQuery("select * from users where email = ?", new String[]{email});
        return cursor;
    }

    public Boolean updatePassword(String email, String password){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        long result = myDatabase.update("users",contentValues,"email = ?", new String[]{email});
        return result != -1;
    }

    public Cursor getEmail(String username){
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        Cursor cursor = myDatabase.rawQuery("select * from users where username = ?", new String[]{username});
        return cursor;
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return new String(hashedPassword, StandardCharsets.UTF_8);
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public void insertCity(String email, String name, String arrivedOn, String leftOn, float temperature, float latitude, float longitude, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("name", name);
        values.put("arrived_on", arrivedOn);
        values.put("left_on", leftOn);
        values.put("temperature", temperature);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("image", image);
        db.insert("Cities", null, values);
        db.close();
    }

}
