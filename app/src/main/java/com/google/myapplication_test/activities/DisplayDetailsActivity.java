package com.google.myapplication_test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.myapplication_test.R;
import com.google.myapplication_test.api.Coordinates;
import com.google.myapplication_test.api.Example;
import com.google.myapplication_test.api.ExampleCoord;
import com.google.myapplication_test.api.Main;
import com.google.myapplication_test.api.WeatherApi;
import com.google.myapplication_test.database.AppDatabase;
import com.google.myapplication_test.database.City;
import com.google.myapplication_test.database.CityDao;
import com.google.myapplication_test.database.User;
import com.google.myapplication_test.database.UserDao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayDetailsActivity extends AppCompatActivity {

    private TextView tripNameDetails, destinationDetails, arrivingDateDetails, leavingDateDetails, weatherDetails, latitudeDetails, longitudeDetails;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}";
    String apiKey = "8f0bd97aaf59233622f5cba4c4c86397";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);

        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        String email = intent.getStringExtra("email");
        String destination = intent.getStringExtra("destination");

        setupViews();
        displayTheDetails(email, tripName);
        getWeather(destination);
    }

    private void displayTheDetails(String mail, String tripName) {
        AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());
        UserDao userDao = appDatabase.userDao();
        CityDao cityDao = appDatabase.cityDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = userDao.email(mail);
                if (user == null) {
                    runOnUiThread(() -> Toast.makeText(DisplayDetailsActivity.this, "Data was not loaded", Toast.LENGTH_SHORT).show());
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            City city = cityDao.getCityByTripNameAndUserEmail(tripName, mail);
                            if (city == null) {
                                runOnUiThread(() -> Toast.makeText(DisplayDetailsActivity.this, "Data was not loaded", Toast.LENGTH_SHORT).show());
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tripNameDetails.setText(tripNameDetails.getText().toString() + ": " + city.getTripName());
                                        destinationDetails.setText(destinationDetails.getText().toString() + ": " + city.getDestination());
                                        arrivingDateDetails.setText(arrivingDateDetails.getText().toString() + ": " + city.getStartDate());
                                        leavingDateDetails.setText(leavingDateDetails.getText().toString() + ": " + city.getEndDate());
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        }).start();
    }

    private void setupViews() {
        tripNameDetails = findViewById(R.id.tripNameDetails);
        destinationDetails = findViewById(R.id.destinationDetails);
        arrivingDateDetails = findViewById(R.id.arrivingDateDetails);
        leavingDateDetails = findViewById(R.id.leavingDateDetails);
        weatherDetails = findViewById(R.id.weatherDetails);
        latitudeDetails = findViewById(R.id.latitudeDetails);
        longitudeDetails = findViewById(R.id.longitudeDetails);
    }

    private void getWeather(String dest) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi api = retrofit.create(WeatherApi.class);
        Call<Example> exampleCall = api.getWeather(dest, apiKey);
        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404) {
                    Toast.makeText(DisplayDetailsActivity.this, "Not a valid city!", Toast.LENGTH_SHORT).show();
                } else if (!response.isSuccessful()) {
                    Toast.makeText(DisplayDetailsActivity.this, Integer.toString(response.code()), Toast.LENGTH_SHORT).show();
                }
                Example myData = response.body();
                Main main = myData.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int) (temp - 273.15);

                int feelsLike = (int) (main.getFeelsLike()-273.15);
                int humidity = main.getHumidity();
                weatherDetails.setText("Weather: " + String.valueOf(temperature) + "C");
                latitudeDetails.setText("Feels like: " + String.valueOf(feelsLike)+"C");
                longitudeDetails.setText("Humidity: " + String.valueOf(humidity)+"%");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(DisplayDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}