package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button login;
    TextView signup;
    private  DatePickerDialog datePickerDialog;
    private Button dateButton;
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignup);
        initdatepicker();
        dateButton=findViewById(R.id.datePickerButton);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Signup.class);
                startActivity(intent2);
            }
        });

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
        } if (month ==1){
            return "MAR";
        } if (month ==1){
            return "APR";
        } if (month ==1){
            return "MAY";
        } if (month ==1){
            return "JUN";
        } if (month ==1){
            return "JUL";
        } if (month ==1){
            return "AUG";
        } if (month ==1){
            return "SEP";
        } if (month ==1){
            return "OCT";
        } if (month ==1){
            return "NOV";
        } if (month ==1){
            return "DEC";
        }
        //Return Default (should never be reached)
        return "JAN";

    }

    ;

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void launchthirdActivity(View view) {
        Log.d(LOG_TAG, "Welcome to admin portal!");
        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);


    }
}