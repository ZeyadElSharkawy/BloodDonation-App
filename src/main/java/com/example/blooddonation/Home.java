package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView name, age, type;
    private static final String LOG_TAG =
            Home.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name = findViewById(R.id.textName);
        age = findViewById(R.id.Age);
        type = findViewById(R.id.Type);


        Intent myIntent = getIntent();
        String NAME = myIntent.getStringExtra("NAME");
        String AGE = myIntent.getStringExtra("AGE");
        String TYPE = myIntent.getStringExtra("TYPE");

        name.setText(NAME);
        age.setText(AGE);
        type.setText(TYPE);

    }



    public void gotoDate(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, Daytime.class);
        startActivity(intent);
    }
}