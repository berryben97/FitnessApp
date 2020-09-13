package com.example.ben.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CalorieCounter extends AppCompatActivity {

    EditText edtHeight3,edtWeight3,edtAge;

    Spinner genderspinner, activityspinner;

    Button btnCalculate;

    TextView CalorieNeeded;

    String selected_gender, selected_activity;

    //firebase
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);

        //setting edittext, spinner, button ,and textview
        edtHeight3 = findViewById(R.id.edtHeight3);
        edtWeight3 = findViewById(R.id.edtWeight3);
        edtAge = findViewById(R.id.edtAge);
        genderspinner = findViewById(R.id.genderspinner);
        activityspinner = findViewById(R.id.activityspinner);
        btnCalculate = findViewById(R.id.btnCalculate);
        CalorieNeeded = findViewById(R.id.calorieneeded);

        String[] gender = new String[]{"Male", "Female"};
        String[] activity = new String[]{"None", "Light", "Normal", "Heavy"};

        final ArrayAdapter<String> genderadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gender);
        ArrayAdapter<String> activityadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, activity);

        genderspinner.setAdapter(genderadapter);
        activityspinner.setAdapter(activityadapter);

        //getting the item position in the spinners
        genderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_gender = genderspinner.getItemAtPosition(position).toString();
                /*Toast.makeText(CalorieCounter.this, ""+ selected_gender, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        activityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_activity = activityspinner.getItemAtPosition(position).toString();
                /*Toast.makeText(CalorieCounter.this, ""+selected_activity, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String heightStr = edtHeight3.getText().toString();
                final String weightStr = edtWeight3.getText().toString();
                final String age = edtAge.getText().toString();

                //error checking
                if (TextUtils.isEmpty(heightStr)){
                    edtHeight3.setError("Please enter height!");
                    edtHeight3.requestFocus();
                    return;
                }
                if (Float.parseFloat(heightStr) > 1000){
                    edtHeight3.setError("Please enter your proper height!");
                    edtHeight3.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(weightStr)){
                    edtWeight3.setError("Please enter weight!");
                    edtWeight3.requestFocus();
                    return;
                }
                if (Float.parseFloat(weightStr) > 250){
                    edtWeight3.setError("Please enter your proper weight!");
                    edtWeight3.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(age)){
                    edtAge.setError("Please enter age!");
                    edtAge.requestFocus();
                    return;
                }
                if (Float.parseFloat(age) > 100){
                    edtAge.setError("Please enter your proper age!");
                    edtAge.requestFocus();
                    return;
                }
                if (selected_gender.matches("")){
                    Toast.makeText(CalorieCounter.this, "Please select a gender!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (selected_activity.matches("")){
                    Toast.makeText(CalorieCounter.this, "Please select a activity!", Toast.LENGTH_LONG).show();
                    return;
                }

                //initialization of double
                double bmr = 0.0;
                double dailyCalorieIntake = 0.0;
                double height = Float.parseFloat(heightStr);
                double weight = Float.parseFloat(weightStr);
                double profage = Float.parseFloat(age);

                //bmr formula is by MIfflin - St Jeor (https://www.thecalculatorsite.com/health/bmr-calculator.php)
                if (selected_gender.matches("Female")){
                    bmr = (height * 6.25) + (weight * 10) - (profage * 5) - 161; //formula for female
                }
                else if (selected_gender.matches("Male")) { //if male is selected
                    bmr = (height * 6.25) + (weight * 10) - (profage * 5) + 5; //formula for male
                }

                if (selected_activity.matches("None")){
                    dailyCalorieIntake = bmr * 1.2;
                }
                else if (selected_activity.matches("Light")){
                    dailyCalorieIntake = bmr * 1.375;
                }
                else if (selected_activity.matches("Normal")){
                    dailyCalorieIntake = bmr * 1.55;
                }
                else if (selected_activity.matches("Heavy")){
                    dailyCalorieIntake = bmr * 1.725;
                }
                final String CaloriIntake = String.format("%.2f",dailyCalorieIntake);

                CalorieNeeded.setText("Your daily calorie intake would be: " + CaloriIntake +"/day");

                database =  FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                database.child("calorieIntake").setValue(CaloriIntake);

            }
        });
    }
}
