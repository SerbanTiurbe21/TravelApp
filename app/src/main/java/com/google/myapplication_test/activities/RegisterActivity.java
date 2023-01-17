package com.google.myapplication_test.activities;

import static com.google.myapplication_test.utilities.Utility.isValidEmail;
import static com.google.myapplication_test.utilities.Utility.isValidPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
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

    private AppDatabase mDb;
    private UserDao mUserDao;
    private UserViewModel mUserViewModel;
    private Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupViews();
        register();
    }

    private void register() {
        registerButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailAddressReg.getText().toString();
                String username = usernameReg.getText().toString();
                String password = passwordReg.getText().toString();
                String repass = repassReg.getText().toString();
                final User user = new User(email, username, password);
                if (validateInputs(email, username, password, repass)) {
                    if (password.equals(repass)) {
                        if (Utility.isValidEmail(email)) {
                            if (Utility.isValidPassword(username)) {
                                if (Utility.isValidPassword(password)) {
                                    AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());
                                    UserDao userDao = appDatabase.userDao();
                                    new Thread(() -> {
                                        User user12 = userDao.email(email);
                                        if(user12 != null){
                                            runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Email is already in use", Toast.LENGTH_SHORT).show());
                                        }
                                        else{
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    User user1 = new User(email,username,password);
                                                    userDao.insert(user1);
                                                }
                                            }).start();
                                            runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show());
                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }).start();
                                    appDatabase.close();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Invalid password!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Invalid username!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Passwords not matching!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please input all the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(String email, String username, String password, String repass) {
        if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty() && !repass.isEmpty()) {
            return true;
        }
        return false;
    }


    private void setupViews() {
        emailAddressReg = findViewById(R.id.emailAddressReg);
        usernameReg = findViewById(R.id.usernameReg);
        passwordReg = findViewById(R.id.passwordReg);
        registerButtonReg = findViewById(R.id.registerButtonReg);
        repassReg = findViewById(R.id.repassReg);
    }
}