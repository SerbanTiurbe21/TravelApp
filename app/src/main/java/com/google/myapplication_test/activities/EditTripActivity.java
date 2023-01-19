package com.google.myapplication_test.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.myapplication_test.R;
import com.google.myapplication_test.database.AppDatabase;
import com.google.myapplication_test.database.City;
import com.google.myapplication_test.database.CityDao;
import com.google.myapplication_test.database.User;
import com.google.myapplication_test.database.UserDao;

public class EditTripActivity extends AppCompatActivity {

    private EditText tripNameEdit,destinationEdit;
    private TextView priceEurEdit, imagePickEdit, testShit2;
    private Slider sliderEdit;
    private RatingBar ratingEdit;
    private ImageView bookmarkEdit;
    private Button saveButtonEdit;
    private Float rateValue;
    private Uri selectedImageUri;
    static final int SELECT_IMAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        setupViews();

        Intent intent = getIntent();
        Boolean bookMarkItem = Boolean.valueOf(intent.getStringExtra("bookMarkItem"));
        String tripName = intent.getStringExtra("tripName");
        String destination = intent.getStringExtra("destination");
        Float price = Float.parseFloat(intent.getStringExtra("price"));
        Float rating = Float.parseFloat(intent.getStringExtra("rating"));
        String email = intent.getStringExtra("email");

        String updatedTripName = tripNameEdit.getText().toString();
        String updatedDestination = destinationEdit.getText().toString();
        String updatedPrice = priceEurEdit.getText().toString();
        String updatedRating = String.valueOf(rateValue);

        updateViews(tripName,destination,price,rating,bookMarkItem);
        updateImage();
        setSlider();
        setRating();
        setImagePickEdit();
        setSaveButtonEdit(email,updatedTripName,updatedDestination,Float.parseFloat(updatedPrice),Float.parseFloat(updatedRating));
    }

    private void setSaveButtonEdit(String mail, String tripName, String destination, Float price, Float rating){
        saveButtonEdit.setOnClickListener(view -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());
            UserDao userDao = appDatabase.userDao();
            CityDao cityDao = appDatabase.cityDao();
            new Thread(() -> {
                // sa bag aici mail-ul
                User user = userDao.email(mail);
                if(user == null){
                    runOnUiThread(() -> Toast.makeText(EditTripActivity.this, "Data was not updated", Toast.LENGTH_SHORT).show());
                }
                else {
                    new Thread(() -> {
                        City city = cityDao.getCityByDestinationAndUserEmail(destination,mail);
                        if(city == null){
                            runOnUiThread(() -> Toast.makeText(EditTripActivity.this, "Data was not updated", Toast.LENGTH_SHORT).show());
                        }
                        else{
                            cityDao.updateCity(tripName,destination,price,rating,mail, String.valueOf(selectedImageUri));
                            runOnUiThread(() -> Toast.makeText(EditTripActivity.this, "Data was updated", Toast.LENGTH_SHORT).show());
                        }
                    }).start();
                }
            }).start();
        });
    }

    private void setupViews(){
        tripNameEdit = findViewById(R.id.tripNameEdit);
        destinationEdit = findViewById(R.id.destinationEdit);
        sliderEdit = findViewById(R.id.sliderEdit);
        ratingEdit = findViewById(R.id.ratingEdit);
        bookmarkEdit = findViewById(R.id.bookmarkEdit);
        priceEurEdit = findViewById(R.id.priceEurEdit);
        imagePickEdit = findViewById(R.id.imagePickEdit);
    }

    private void updateViews(String tripName, String destination, Float price, Float rating, Boolean isBookmarked){
        tripNameEdit.setText(tripName);
        destinationEdit.setText(destination);
        sliderEdit.setValue(price);
        ratingEdit.setRating(rating);
        bookmarkEdit.setImageResource(isBookmarked ? R.drawable.ic_baseline_bookmark__gold_24 : R.drawable.ic_baseline_bookmark_24);
    }

    private void updateImage(){
        bookmarkEdit.setOnClickListener(view -> bookmarkEdit.setImageResource(R.drawable.ic_baseline_bookmark__gold_24));
    }

    private void setSlider(){
        sliderEdit.addOnChangeListener((slider, value, fromUser) -> {
            int index = String.valueOf(value).indexOf(".");
            String text = "Price in EUR: ";
            priceEurEdit.setText(text + String.valueOf(value).substring(0,index));
        });
    }

    private void setRating(){
        ratingEdit.setOnRatingBarChangeListener((ratingBar, v, b) -> rateValue = ratingBar.getRating());
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivity(intent);
        //Intent intent = new Intent();
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,SELECT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Uri uri = data.getData();
            //Log.e("URI",uri.toString());
            selectedImageUri = uri;
        }
    }

    private void setImagePickEdit(){
        imagePickEdit.setOnClickListener(view -> {
            selectImage();
        });
    }

}