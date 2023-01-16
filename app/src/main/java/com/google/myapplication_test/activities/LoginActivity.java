package com.google.myapplication_test.activities;

import static com.google.myapplication_test.utilities.Utility.isValidEmail;
import static com.google.myapplication_test.utilities.Utility.isValidPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.myapplication_test.R;
import com.google.myapplication_test.database.AppDatabase;
import com.google.myapplication_test.database.DatabaseHelper;

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



    private void loginButtonAction() {
        databaseHelper = new DatabaseHelper(this);
        loginButtonLogin.setOnClickListener(view -> {
            String userEmail = emailAddressLogin.getText().toString();
            StringBuilder username = new StringBuilder();
            Cursor cursor = databaseHelper.getUsername(userEmail);
            while(cursor.moveToNext()){
                username.append(cursor.getString(1));
            }
            String password = passwordLogin.getText().toString();
            if (userEmail.equals("") || password.equals("")) {
                Toast.makeText(LoginActivity.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
            } else {
                if (!isValidEmail(userEmail) || (password.length() < 8 && !isValidPassword(password))) {
                    Toast.makeText(LoginActivity.this, "Invalid email address or password!", Toast.LENGTH_SHORT).show();
                } else {
                    //int checkUserPassword = db.userDao().findByEmailAndPassword(emailAddressLogin.getText().toString(),passwordLogin.getText().toString());
                    Boolean checkUserPassword = databaseHelper.checkPasswordForLogin(userEmail, password);
                    if (checkUserPassword) {
                        Toast.makeText(LoginActivity.this, "Sign in successfully!", Toast.LENGTH_SHORT).show();
                        Intent changeActivity = new Intent(LoginActivity.this, MainActivity.class);
                        changeActivity.putExtra("username", (CharSequence) username);
                        changeActivity.putExtra("email",userEmail);
                        startActivity(changeActivity);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
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
        loginButtonAction();
        forgotPassAction();
    }
}