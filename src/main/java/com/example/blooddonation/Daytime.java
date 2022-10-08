package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class Daytime extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button time;
    int hour,minute;
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daytime);
        initdatepicker();
        dateButton=findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        time=findViewById(R.id.time);


    }
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initdatepicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                String date= makeDateString(day, month, year);
                dateButton.setText(date);


            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog= new DatePickerDialog(this,style, dateSetListener, year, month, day);





    }

    private String makeDateString(int day, int month, int year) {
        return getmonthfromat(month)+ "" + day + "" + year;
    }

    private String getmonthfromat(int month) {
        if (month ==1){
            return "JAN";
        } if (month ==2){
            return "FEB";
        } if (month ==3){
            return "MAR";
        } if (month ==4){
            return "APR";
        } if (month ==5){
            return "MAY";
        } if (month ==6){
            return "JUN";
        } if (month ==7){
            return "JUL";
        } if (month ==8){
            return "AUG";
        } if (month ==9){
            return "SEP";
        } if (month ==10){
            return "OCT";
        } if (month ==11){
            return "NOV";
        } if (month ==12){
            return "DEC";
        }
        //Return Default (should never be reached)
        return "JAN";

    }

    ;

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour=selectedHour;
                minute=selectedMinute;
                time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

            }
        };
        TimePickerDialog timePickerDialog= new TimePickerDialog(this, onTimeSetListener, hour, minute, true );
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }

    public void gotoLocation(View view) {
        Log.d(LOG_TAG, "Success");
        Intent intent = new Intent(this, Location.class);
        startActivity(intent);

    }
}