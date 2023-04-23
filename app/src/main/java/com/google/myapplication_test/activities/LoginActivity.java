package com.google.myapplication_test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.myapplication_test.R;
import com.google.myapplication_test.api.user.RetrofitInstance;
import com.google.myapplication_test.api.user.UserService;
import com.google.myapplication_test.login.LoginRequest;
import com.google.myapplication_test.login.LoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button registerButtonLogin, loginButtonLogin;
    EditText emailAddressLogin, passwordLogin;
    TextView forgotPassLogin;

    private void registerButtonAction() {
        registerButtonLogin.setOnClickListener(view -> {
            Intent changeActivity = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(changeActivity);
        });
    }

    private void loginUser(String userEmail, String password){
        final LoginRequest loginRequest = new LoginRequest(userEmail,password);
        UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
        Call<LoginResponse> call = userService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Handle successful login
                    LoginResponse loginResponse = response.body();
                    String successMessage = loginResponse.getMessage();
                    Toast.makeText(LoginActivity.this, successMessage, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("email",userEmail);
                    intent.putExtra("password",password);
                    startActivity(intent);
                } else {
                    // Handle invalid email or password
                    try {
                        String errorMessage = response.errorBody().string();
                        // Show the error message to the user (e.g., using a Toast or TextView)
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle network failure or other issues
                if (t instanceof IOException) {
                    // This is a network error (e.g. no internet connection, timeout)
                    Toast.makeText(LoginActivity.this, "Network error! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    // This is an unexpected error (e.g. a conversion issue in Retrofit)
                    Toast.makeText(LoginActivity.this, "An unexpected error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }

                // You might want to log the error for debugging purposes
                Log.e("RegisterActivity", "Registration API call failed: ", t);
            }
        });
    }

    private void setLoginButtonLogin(){
        loginButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = emailAddressLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                if(userEmail.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please input all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginUser(userEmail,password);
            }
        });
    }

    /*
    private void setLoginButtonLogin() {
        loginButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] username = {""};
                String userEmail = emailAddressLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                if (userEmail.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isValidEmail(userEmail) || (password.length() < 8 && !isValidPassword(password))) {
                        Toast.makeText(LoginActivity.this, "Invalid email address or password!", Toast.LENGTH_SHORT).show();
                    } else {
                        AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());
                        UserDao userDao = appDatabase.userDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                User user = userDao.login(userEmail, password);
                                if (user == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    intent.putExtra("email", user.getEmail());
                                    intent.putExtra("username",user.getName());
                                    startActivityForResult(intent,1);
                                }
                            }
                        }).start();
                        appDatabase.close();
                    }
                }
            }
        });
    }*/

    private void forgotPassAction() {
        forgotPassLogin.setOnClickListener(view -> {
            Intent changeActivity = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(changeActivity);
        });
    }

    private void setupViews() {
        registerButtonLogin = findViewById(R.id.registerButtonLogin);
        loginButtonLogin = findViewById(R.id.loginButtonLogin);
        emailAddressLogin = findViewById(R.id.emailAddressLogin);
        passwordLogin = findViewById(R.id.passwordMain);
        forgotPassLogin = findViewById(R.id.forgotPassLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
        registerButtonAction();
        setLoginButtonLogin();
        forgotPassAction();
    }
}