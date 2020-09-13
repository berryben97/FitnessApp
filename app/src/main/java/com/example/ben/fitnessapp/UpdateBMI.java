package com.example.ben.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ben.fitnessapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateBMI extends AppCompatActivity {

    EditText edtHeight2, edtWeight2;

    Button update;

    TextView currentBMI, updatedBMI, comparison;

    //firebase
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bmi);

        //setting textview, button and edittext

        edtHeight2 = findViewById(R.id.edtHeight2);
        edtWeight2 = findViewById(R.id.edtWeight2);
        update = findViewById(R.id.btnUpdate);
        currentBMI = findViewById(R.id.CurrentBMI2);
        updatedBMI = findViewById(R.id.updatedBMI);
        comparison = findViewById(R.id.comparison);

        //to get the value passed from ProfileActivity.class
        Bundle passed = getIntent().getExtras();
        final String curbmi = passed.getString("curbmi");

        //used to check if the value passed is correct.
        /*Toast.makeText(this, ""+curbmi, Toast.LENGTH_SHORT).show();*/

        //set text for current bmi.
        currentBMI.setText("Your current BMI: " + curbmi);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final String heightStr = edtHeight2.getText().toString();
                    final String weightStr = edtWeight2.getText().toString();


                    if (TextUtils.isEmpty(heightStr)){
                        edtHeight2.setError("Please enter height!");
                        edtHeight2.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(weightStr)){
                        edtWeight2.setError("Please enter weight!");
                        edtWeight2.requestFocus();
                        return;
                    }

                    //bmi calculation
                    float heightValue = Float.parseFloat(heightStr) / 100;
                    float weightValue = Float.parseFloat(weightStr);
                    float bmi = weightValue / (heightValue * heightValue);
                    final String updateBMI = String.format("%.2f",bmi);

                    //setting the new bmi to be shown to the users.
                    updatedBMI.setText("Your new BMI: " + updateBMI);

                    //comparing the updatedBMI and currentBMI to see the progress for users.
                    if(Float.parseFloat(updateBMI) < Float.parseFloat(curbmi)){

                        comparison.setText("Based on your updated BMI and current BMI. You have made some great improvements. Keep up the good work!");

                    }
                    else if (Float.parseFloat(updateBMI) > Float.parseFloat(curbmi)){

                        comparison.setText("Based on your updated BMI and current BMI. You have not made any great improvements. Please keep motivating yourself more and work harder!");

                    }
                    else{

                        comparison.setText("Based on your updated BMI and current BMI. You have not made any improvements and your BMI is still the same. Stay healthy and exercise always.");
                    }

                    database =  FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    database.child("currentBmi").setValue(updateBMI);

                    Toast.makeText(UpdateBMI.this, "Updated successfully.", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
