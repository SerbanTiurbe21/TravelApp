package com.google.myapplication_test.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.myapplication_test.R;
import com.google.myapplication_test.api.trip.TripService;
import com.google.myapplication_test.api.user.RetrofitInstance;
import com.google.myapplication_test.api.user.UserService;
import com.google.myapplication_test.database.AppDatabase;
import com.google.myapplication_test.database.City;
import com.google.myapplication_test.database.CityDao;
import com.google.myapplication_test.database.User;
import com.google.myapplication_test.database.UserDao;
import com.google.myapplication_test.entities.TripDB;
import com.google.myapplication_test.fragments.trip.HomeFragment;
import com.google.myapplication_test.fragments.trip.Trip;
import com.google.myapplication_test.fragments.trip.TripAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDestinationActivity extends AppCompatActivity {

    private Uri selectedImageUri;
    static final int SELECT_IMAGE_CODE = 1;
    private Float rateValue;

    EditText tripNameDest, destinationDest, arrivingDateDestination, leavingDateDestination;
    TextView priceEurDest, imagePickDestination, testShit;
    RadioGroup radioGroupDest;
    RadioButton cityBreakDest, seaSideDest, mountainDest, radioButton;
    Slider slider;
    RatingBar ratingDest;
    Button saveButtonDest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        Log.d("caca", email);

        setupViews();
        setImagePickDestination();
        setupCalendar(arrivingDateDestination);
        setSlider();
        setRatingDest();
        test_button(email);
    }

    public void saveTrip(TripDB trip, String email) {
        TripService tripService = RetrofitInstance.getRetrofitInstance().create(TripService.class);
        Call<TripDB> call = tripService.saveTrip(trip);

        call.enqueue(new Callback<TripDB>() {
            @Override
            public void onResponse(Call<TripDB> call, Response<TripDB> response) {
                if (response.isSuccessful()) {
                    TripDB savedTrip = response.body();
                    // Handle the saved trip object
                    Toast.makeText(getApplicationContext(), "Trip saved successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddDestinationActivity.this,MainActivity.class);
                    //intent.putExtra("tripName",savedTrip.getTripName());
                    //intent.putExtra("destination",savedTrip.getDestination());
                    //intent.putExtra("price",savedTrip.getPrice());
                    //intent.putExtra("stars",savedTrip.getRating());
                    //intent.putExtra("linkImage",savedTrip.getPhotoUri());
                    intent.putExtra("email", email);
                    startActivityForResult(intent,2);
                } else {
                    // Handle the error
                    Toast.makeText(getApplicationContext(), "Error saving trip: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TripDB> call, Throwable t) {
                // Handle network error
                Toast.makeText(getApplicationContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void test_button(String email){
        saveButtonDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tripName = tripNameDest.getText().toString();
                String destination = destinationDest.getText().toString();
                String radioButtonValue = testShit.getText().toString();
                String price = getValue(priceEurDest.getText().toString());
                String arrivingDate = arrivingDateDestination.getText().toString();
                String leavingDate = leavingDateDestination.getText().toString();
                String stars = String.valueOf(rateValue);
                String linkImage = String.valueOf(selectedImageUri);
                UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
                Call<User> call = userService.getUserByEmail(email);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User user = response.body();
                            Log.d("user email", user.getEmail());
                            // Create a new TripDB object and set the user
                            if(tripName.isEmpty() || destination.isEmpty() || radioButtonValue.isEmpty() || arrivingDate.isEmpty() || leavingDate.isEmpty() || price.isEmpty() ||stars.isEmpty() || linkImage.isEmpty()){
                                Log.d("tripName", tripName);
                                Log.d("dest",destination);
                                Log.d("city_break", radioButtonValue);
                                Log.d("price", price);
                                Log.d("date1", arrivingDate);
                                Log.d("date2", leavingDate);
                                Log.d("stars", stars);
                                Log.d("linkimg", linkImage);
                                return;
                            }
                            TripDB trip = new TripDB(user, tripName, arrivingDate, leavingDate, destination, radioButtonValue, Float.parseFloat(price), Float.parseFloat(stars), linkImage, 0f, false);
                            Log.d("trip link", trip.getPhotoUri());
                            // Save the trip using the saveTrip method
                            saveTrip(trip, email);
                            Toast.makeText(getApplicationContext(), "User fetched successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle the error
                            Toast.makeText(getApplicationContext(), "Error fetching user: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        // Handle network error
                        Toast.makeText(getApplicationContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /*
    private void setSaveButtonDest(String mail){
        saveButtonDest.setOnClickListener(view -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());
            UserDao userDao = appDatabase.userDao();
            CityDao cityDao = appDatabase.cityDao();
            String tripName = tripNameDest.getText().toString();
            String destination = destinationDest.getText().toString();
            String radioButtonValue = testShit.getText().toString();
            String price = priceEurDest.getText().toString();
            String arrivingDate = arrivingDateDestination.getText().toString();
            String leavingDate = leavingDateDestination.getText().toString();
            String stars = String.valueOf(rateValue);
            String linkImage = String.valueOf(selectedImageUri);
            City city = new City(mail,tripName,destination,radioButtonValue,Float.parseFloat(getValue(price)),arrivingDate,leavingDate,Float.parseFloat(stars),linkImage,0,0,0,false);

            new Thread(() -> {
                User user = userDao.email(mail);
                if(user == null){
                    runOnUiThread(() -> Toast.makeText(AddDestinationActivity.this, "Data was not inserted", Toast.LENGTH_SHORT).show());
                }
                else{
                    new Thread(() -> cityDao.insert(city)).start();
                    Intent intent = new Intent(AddDestinationActivity.this,MainActivity.class);
                    intent.putExtra("tripName",tripName);
                    intent.putExtra("destination",destination);
                    intent.putExtra("price",price);
                    intent.putExtra("stars",stars);
                    intent.putExtra("linkImage",linkImage);
                    intent.putExtra("email",mail);
                    runOnUiThread(() -> Toast.makeText(AddDestinationActivity.this,"Data was inserted",Toast.LENGTH_SHORT).show());
                    startActivityForResult(intent,2);
                    //finish();
                }
            }).start();
            appDatabase.close();
        });
    }*/

    private void setupCalendar(EditText myView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
                updateDateRange(year, month, dayOfMonth, leavingDateDestination);
            }

            private void updateCalendar() {
                String format = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.UK);
                myView.setText(sdf.format(calendar.getTime()));
            }
        };
        myView.setOnClickListener(view -> {
            new DatePickerDialog(AddDestinationActivity.this, date, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void updateDateRange(int year1, int month1, int day1, EditText editText) {
        // Set up the calendar with the selected date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year1, month1, day1);

        // Set an OnClickListener on the second EditText to show the DatePickerDialog when clicked
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set up the calendar with the current date
                Calendar calendar1 = Calendar.getInstance();
                int year = calendar1.get(Calendar.YEAR);
                int month = calendar1.get(Calendar.MONTH);
                int day = calendar1.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog with a custom DatePicker
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDestinationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // Set the second EditText to the selected date
                        String format = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.UK);
                        editText.setText(sdf.format(calendar1.getTime()));
                    }
                }, year, month, day);

                // Get the DatePicker from the DatePickerDialog
                DatePicker datePicker = datePickerDialog.getDatePicker();

                // Set the min and max dates of the DatePicker based on the selected date
                datePicker.setMinDate(calendar.getTimeInMillis());

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });
    }

    private void setupViews() {
        tripNameDest = findViewById(R.id.tripNameDest);
        destinationDest = findViewById(R.id.destinationDest);
        arrivingDateDestination = findViewById(R.id.arrivingDateDestination);
        leavingDateDestination = findViewById(R.id.leavingDateDestination);
        priceEurDest = findViewById(R.id.priceEurDest);
        imagePickDestination = findViewById(R.id.imagePickDestination);
        radioGroupDest = findViewById(R.id.radioGroupDest);
        cityBreakDest = findViewById(R.id.cityBreakDest);
        seaSideDest = findViewById(R.id.seaSideDest);
        mountainDest = findViewById(R.id.mountainDest);
        slider = findViewById(R.id.slider);
        ratingDest = findViewById(R.id.ratingDest);
        testShit = findViewById(R.id.testShit);
        saveButtonDest = findViewById(R.id.saveButtonDest);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivity(intent);
        //Intent intent = new Intent();
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Uri uri = data.getData();
            Log.e("URI",uri.toString());
            selectedImageUri = uri;
        }
    }

    private void setImagePickDestination() {
        imagePickDestination.setOnClickListener(view -> {
            selectImage();
        });
    }

    public void onRadioButtonClicked(View view) {
        int radioId = radioGroupDest.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        testShit.setText(radioButton.getText());
    }

    private void setSlider() {
        slider.addOnChangeListener((slider, value, fromUser) -> {
            int index = String.valueOf(value).indexOf(".");
            String text = "Price in EUR: ";
            priceEurDest.setText(text + String.valueOf(value).substring(0, index));
        });
    }

    private void setRatingDest() {
        ratingDest.setOnRatingBarChangeListener((ratingBar, v, b) -> rateValue = ratingBar.getRating());
    }

    private static String getValue(String str) {
        String[] parts = str.split(":");
        return parts[parts.length - 1].trim();
    }

    public static Float extractNumber(String input) {
        String number = input.replaceAll("[^0-9]", "");
        return Float.parseFloat(number);
    }
}