package com.example.ben.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ExerciseChoiceActivity extends AppCompatActivity {

    ImageButton btnDumbbells, btnBodyweight;
    String exercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_choice);

        //getting the passed choice result.
        Bundle choice = getIntent().getExtras();
        final String parts = choice.getString("key");

        /*Toast.makeText(this, "Please work:" + parts, Toast.LENGTH_SHORT).show();*/

        btnDumbbells = findViewById(R.id.btnDumbbells);
        btnBodyweight = findViewById(R.id.btnBodyweight);

        btnDumbbells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise = "Equipment";
                Intent intenta = new Intent(ExerciseChoiceActivity.this,ExerciseActivity.class);
                Bundle extras = new Bundle();
                extras.putString("Parts", parts);
                extras.putString("Choice", exercise);
                intenta.putExtras(extras);
                startActivity(intenta);
            }
        });

        btnBodyweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise = "Bodyweight";
                Intent intenta = new Intent(ExerciseChoiceActivity.this,ExerciseActivity.class);
                Bundle extras = new Bundle();
                extras.putString("Parts", parts);
                extras.putString("Choice", exercise);
                intenta.putExtras(extras);
                startActivity(intenta);
            }
        });
    }
}
