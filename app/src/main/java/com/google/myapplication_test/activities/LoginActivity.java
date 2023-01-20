package com.google.myapplication_test.activities;

import static com.google.myapplication_test.utilities.Utility.isValidEmail;
import static com.google.myapplication_test.utilities.Utility.isValidPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.myapplication_test.R;
import com.google.myapplication_test.database.AppDatabase;
import com.google.myapplication_test.database.DatabaseHelper;
import com.google.myapplication_test.database.User;
import com.google.myapplication_test.database.UserDao;

public class LoginActivity extends AppCompatActivity {

    Button registerButtonLogin, loginButtonLogin;
    EditText emailAddressLogin, passwordLogin;
    DatabaseHelper databaseHelper;
    TextView forgotPassLogin;

    private void registerButtonAction() {
        registerButtonLogin.setOnClickListener(view -> {
            Intent changeActivity = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(changeActivity);
        });
    }


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
    }

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