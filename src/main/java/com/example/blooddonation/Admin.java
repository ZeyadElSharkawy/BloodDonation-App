package com.example.blooddonation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Admin extends AppCompatActivity {
    EditText name, contact, dob, btype, time, date;
    Button insert, update, delete, view;
    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        btype=findViewById(R.id.btype);
        time=findViewById(R.id.Time1);
        date=findViewById(R.id.date1);

        insert = findViewById(R.id.btninsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();
                String btypeTXT= btype.getText().toString();
                String dateTXT= date.getText().toString();
                String timeTXT= time.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(nameTXT, contactTXT, dobTXT, btypeTXT, dateTXT, timeTXT);
                if(checkinsertdata==true)
                    Toast.makeText(Admin.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Admin.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();

                String btypeTXT= btype.getText().toString();
                String timeTXT = time.getText().toString();
                String dateTXT = date.getText().toString();
                Boolean checkupdatedata = DB.updateuserdata(nameTXT, contactTXT, dobTXT, btypeTXT, timeTXT, dateTXT );
                if(checkupdatedata==true)
                    Toast.makeText(Admin.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Admin.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
            }        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                Boolean checkudeletedata = DB.deletedata(nameTXT);
                if(checkudeletedata==true)
                    Toast.makeText(Admin.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Admin.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(Admin.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("Contact :"+res.getString(1)+"\n");
                    buffer.append("Date of Birth :"+res.getString(2)+"\n\n");
                    buffer.append("Blood Type: "+res.getString(3)+"\n\n");
                    buffer.append("Time of Appointment: "+res.getString(4)+"\n\n");
                    buffer.append("Date of Appointment: "+res.getString(5)+"\n\n");

                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
    }}