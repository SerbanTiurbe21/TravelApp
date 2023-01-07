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

import com.google.android.material.slider.Slider;
import com.google.myapplication_test.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

        setupViews();
        setImagePickDestination();
        setupCalendar(arrivingDateDestination);
        setSlider();
        String radioButtonValue = testShit.getText().toString();
        setRatingDest();
        String arrivingDate = arrivingDateDestination.getText().toString();
        String leavingDate = leavingDateDestination.getText().toString();
        //RAMANE SA LE ADAUG IN BAZA DE DATE
        setSaveButtonDest();
    }

    private void setupCalendar(EditText myView){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
                updateDateRange(year,month,dayOfMonth,leavingDateDestination);
            }
            private void updateCalendar(){
                String format = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.UK);
                myView.setText(sdf.format(calendar.getTime()));
            }
        };
        myView.setOnClickListener(view -> {
            new DatePickerDialog(AddDestinationActivity.this, date, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                        /*
                        if(month + 1 < 10){
                            editText.setText("0"+day + "/" + "0"+(month + 1) + "/" + year);
                        }
                        else{
                            editText.setText("0"+day + "/" +(month + 1) + "/" + String.);
                        }*/
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

    private void setupViews(){
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

    private void setImagePickDestination(){
        imagePickDestination.setOnClickListener(view -> {
            selectImage();
        });
    }

    public void onRadioButtonClicked(View view){
        /*
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.cityBreakDest:
                if(checked){
                    radioButtonValue = cityBreakDest.getText().toString();
                }
                break;
            case R.id.seaSideDest:
                if(checked){
                    radioButtonValue = seaSideDest.getText().toString();
                }
                break;
            case R.id.mountainDest:
                if(checked){
                    radioButtonValue = mountainDest.getText().toString();
                }
                break;
        }*/
        int radioId = radioGroupDest.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        testShit.setText(radioButton.getText());
    }

    private void setSlider(){
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int index = String.valueOf(value).indexOf(".");
                String text = "Price in EUR: ";
                priceEurDest.setText(text + String.valueOf(value).substring(0,index));
            }
        });
    }

    private void setRatingDest(){
        ratingDest.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue = ratingBar.getRating();
            }
        });
    }

    private void setSaveButtonDest(){
        saveButtonDest.setOnClickListener(view -> {
            // aici le salvez in baza de date
        });
    }
}