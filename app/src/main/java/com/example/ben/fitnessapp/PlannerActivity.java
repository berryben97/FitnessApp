package com.example.ben.fitnessapp;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class PlannerActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(PlannerActivity.this,MainMenuActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_planner:
                    return true;
                case R.id.navigation_account:
                    Intent intent2 = new Intent(PlannerActivity.this,AccountActivity.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }
    };

    //imagebutton
    ImageButton workout;
    ImageButton meal;

    String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        //set buttons
        workout = (ImageButton)findViewById(R.id.workout);
        meal = (ImageButton)findViewById(R.id.meal);

        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = "Workout";
                Intent intenta = new Intent(PlannerActivity.this,ListOfMealOrWorkoutActivity.class);
                intenta.putExtra("choice",choice);
                startActivity(intenta);
            }
        });

        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choice = "Meal";
                Intent intenta = new Intent(PlannerActivity.this,ListOfMealOrWorkoutActivity.class);
                intenta.putExtra("choice",choice);
                startActivity(intenta);

            }
        });
    }
}
