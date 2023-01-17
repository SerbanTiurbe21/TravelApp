package com.google.myapplication_test.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.myapplication_test.utilities.Utility;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailAddressForgot, oldPasswordForgot, newPasswordForgot, reNewPasswordForgot;
    Button resetButtonForget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        setupViews();
        forgotPasswordButtonAction();
    }

    private void setupViews() {
        emailAddressForgot = findViewById(R.id.emailAddressForgot);
        oldPasswordForgot = findViewById(R.id.oldPasswordForgot);
        newPasswordForgot = findViewById(R.id.newPasswordForgot);
        reNewPasswordForgot = findViewById(R.id.reNewPasswordForgot);
        resetButtonForget = findViewById(R.id.resetButtonForgot);
    }

    private void forgotPasswordButtonAction() {
        resetButtonForget.setOnClickListener(view -> {
            String emailAddress = emailAddressForgot.getText().toString();
            String oldPassword = oldPasswordForgot.getText().toString();
            String newPassword = newPasswordForgot.getText().toString();
            String reNewPassword = reNewPasswordForgot.getText().toString();

            AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());
            UserDao userDao = appDatabase.userDao();

            new Thread(() -> {
                User user = userDao.email(emailAddress);
                if (user == null) {
                    runOnUiThread(() -> Toast.makeText(ForgotPasswordActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show());
                } else {
                    if (Utility.isValidPassword(newPassword)) {
                        if (newPassword.equals(oldPassword)) {
                            runOnUiThread(() -> Toast.makeText(ForgotPasswordActivity.this, "New password cannot be the same as the old one!", Toast.LENGTH_SHORT).show());
                        } else {
                            if (reNewPassword.equals(newPassword)) {
                                new Thread(() -> {
                                    userDao.updatePassword(emailAddress, newPassword);
                                }).start();
                                runOnUiThread(() -> Toast.makeText(ForgotPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show());
                            } else {
                                runOnUiThread(() -> Toast.makeText(ForgotPasswordActivity.this, "Passwords not matching!", Toast.LENGTH_SHORT).show());
                            }
                        }
                    } else {
                        runOnUiThread(() -> Toast.makeText(ForgotPasswordActivity.this, "Invalid password!", Toast.LENGTH_SHORT).show());
                    }
                }
            }).start();
            appDatabase.close();
        });
    }

}