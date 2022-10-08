package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {

    EditText email, name, age, password;
    Spinner spinner;
    Button signup;
    TextView login;
    public String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        name = findViewById(R.id.fullName);
        age = findViewById(R.id.age);
        password = findViewById(R.id.password);
        spinner = findViewById(R.id.typeSpinner);
        signup = findViewById(R.id.signupBtn);
        login = findViewById(R.id.goToLogin);

        String[] typesList = {"Select blood type", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, typesList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "Please select blood type", Toast.LENGTH_SHORT).show();
                }
                else {
                    str = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myName = name.getText().toString();
                String myAge = age.getText().toString();
                String myType = spinner.getSelectedItem().toString();

                Intent intent = new Intent(Signup.this, Home.class);
                intent.putExtra("NAME", myName);
                intent.putExtra("AGE", myAge);
                intent.putExtra("TYPE", myType);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });





    }
}