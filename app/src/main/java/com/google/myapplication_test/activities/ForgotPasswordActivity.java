package com.google.myapplication_test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.myapplication_test.R;
import com.google.myapplication_test.database.DatabaseHelper;
import com.google.myapplication_test.utilities.Utility;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailAddressForgot, oldPasswordForgot, newPasswordForgot, reNewPasswordForgot;
    Button resetButtonForget;
    DatabaseHelper databaseHelper;

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
        databaseHelper = new DatabaseHelper(this);
        resetButtonForget.setOnClickListener(view -> {
            String emailAddress = emailAddressForgot.getText().toString();
            String oldPassword = oldPasswordForgot.getText().toString();
            String newPassword = newPasswordForgot.getText().toString();
            String reNewPassword = reNewPasswordForgot.getText().toString();
            if (databaseHelper.checkEmail(emailAddress)) {
                if (Utility.isValidPassword(newPassword)) {
                    if (newPassword.equals(oldPassword)) {
                        Toast.makeText(this, "New password cannot be the same as the old one!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (reNewPassword.equals(newPassword)) {
                            Boolean checkPasswordUpdate = databaseHelper.updatePassword(emailAddress, newPassword);
                            if (checkPasswordUpdate) {
                                Intent changeActivity = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                                startActivity(changeActivity);
                                Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Password not updated successfully!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Passwords not matching!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "Invalid password!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid email address!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}