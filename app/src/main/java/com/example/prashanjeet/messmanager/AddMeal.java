package com.example.prashanjeet.messmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class AddMeal extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    Button add;
    EditText title,cost,des;
    TextView date;
    public DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        add = (Button) findViewById(R.id.meal_add);
        title= (EditText) findViewById(R.id.meal_title);
        des = (EditText) findViewById(R.id.meal_description);
        cost = (EditText) findViewById(R.id.meal_cost);
        date = (TextView) findViewById(R.id.meal_date);
        databaseUsers = FirebaseDatabase.getInstance().getReference("meals");
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = databaseUsers.push().getKey();
                String tit = title.getText().toString();
                String desc = des.getText().toString();
                String co = cost.getText().toString();
                String d = date.getText().toString();
                Meal meal = new Meal(tit,desc,co,d);

                try {
                    databaseUsers.child(id).setValue(meal);
                    Toast.makeText(AddMeal.this,"Meal Uploaded",Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AddMeal.this,"Network error please try later",Toast.LENGTH_LONG).show();
                }

                Intent intent  = new  Intent(AddMeal.this ,ManagerHome.class);
                startActivity(intent);

            }
        });


    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        date.setText(currentDate);
    }
}