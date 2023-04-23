package com.google.myapplication_test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.myapplication_test.R;
import com.google.myapplication_test.api.user.RetrofitInstance;
import com.google.myapplication_test.api.user.UserService;
import com.google.myapplication_test.utilities.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private void forgotPasswordButtonAction(){
        resetButtonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = emailAddressForgot.getText().toString();
                String oldPassword = oldPasswordForgot.getText().toString();
                String newPassword = newPasswordForgot.getText().toString();
                String reNewPassword = reNewPasswordForgot.getText().toString();

                if(emailAddress.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty() || reNewPassword.isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Utility.isValidPassword(newPassword)){
                    Toast.makeText(ForgotPasswordActivity.this, "New password is invalid!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(newPassword.equals(oldPassword)){
                    Toast.makeText(ForgotPasswordActivity.this, "New password cannot be the same as the old one!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!newPassword.equals(reNewPassword)){
                    Toast.makeText(ForgotPasswordActivity.this, "Passwords not matching!", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
                Call<Void> call = userService.resetPassword(emailAddress, oldPassword, newPassword, reNewPassword);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.code() == 404) { // HttpStatus.NOT_FOUND
                                Toast.makeText(ForgotPasswordActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Error resetting password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ForgotPasswordActivity.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /*
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
    }*/

}