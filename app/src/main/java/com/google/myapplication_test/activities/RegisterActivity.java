package com.google.myapplication_test.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.myapplication_test.R;
import com.google.myapplication_test.database.AppDatabase;
import com.google.myapplication_test.database.DatabaseHelper;
import com.google.myapplication_test.database.User;
import com.google.myapplication_test.database.UserDao;
import com.google.myapplication_test.database.UserViewModel;
import com.google.myapplication_test.utilities.Utility;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    EditText emailAddressReg, usernameReg, passwordReg, repassReg;
    Button registerButtonReg;
    DatabaseHelper databaseHelper;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
        setRegisterButtonReg();
    }

    private void setRegisterButtonReg() {
        Executor executor = Executors.newSingleThreadExecutor();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        registerButtonReg.setOnClickListener(view -> {
            String email = emailAddressReg.getText().toString();
            String username = usernameReg.getText().toString();
            String password = passwordReg.getText().toString();
            String repass = repassReg.getText().toString();
            if (email.equals("") || username.equals("") || password.equals("") || repass.equals("")) {
                Toast.makeText(RegisterActivity.this, "Please input all the fields!", Toast.LENGTH_SHORT).show();
            } else {
                if (!Utility.isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                } else {
                    if (!Utility.isValidPassword(password)) {
                        Toast.makeText(RegisterActivity.this, "Invalid password!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!Utility.isValidUsername(username)) {
                            Toast.makeText(RegisterActivity.this, "Invalid username!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (password.equals(repass)) {
                                Boolean checkUsername = userViewModel.checkUsersName(username);
                                Boolean checkEmail = userViewModel.checkUsersEmail(email);
                                if (!checkEmail && !checkUsername) {
                                    User user = new User(email,username,password,null);
                                    userViewModel.insert(user);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "User already exists! Please sign in!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Passwords not matching!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    private void setupViews() {
        emailAddressReg = findViewById(R.id.emailAddressReg);
        usernameReg = findViewById(R.id.usernameReg);
        passwordReg = findViewById(R.id.passwordReg);
        registerButtonReg = findViewById(R.id.registerButtonReg);
        repassReg = findViewById(R.id.repassReg);
    }
}