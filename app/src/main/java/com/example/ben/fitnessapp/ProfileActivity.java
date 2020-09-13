package com.example.ben.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView fullname, currentBMI, BMIlabel, calorieintake;

    String bmiLabel,curbmi;

    Button signout;

    //firebaseAuth
    FirebaseAuth auth;

    //firebase
    DatabaseReference database;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(ProfileActivity.this,MainMenuActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_planner:
                    Intent intent2 = new Intent(ProfileActivity.this,PlannerActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_account:
                    //mTextMessage.setText("Account");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get firebaseauth instance
        auth = FirebaseAuth.getInstance();

        //set textview
        fullname = (TextView) findViewById(R.id.fullname);
        currentBMI = (TextView) findViewById(R.id.currentBMI);
        BMIlabel = (TextView) findViewById(R.id.BMIlabel);
        calorieintake = (TextView) findViewById(R.id.CalorieIntake);

/*        //set temporary button
        signout = (Button) findViewById(R.id.Signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this, AccountActivity.class));
            }
        });*/

        loadCurrentUserInfo();

    }

    private void loadCurrentUserInfo() {

        /*FirebaseUser user = auth.getCurrentUser();*///used to get current user.

        database =  FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fullname").getValue().toString();
                fullname.setText(name);

                curbmi = dataSnapshot.child("currentBmi").getValue().toString();
                currentBMI.setText(curbmi);

                //This is used to compare the bmi and prompt your bmi rating
                if (Float.compare(Float.parseFloat(curbmi), 15f) <= 0) {
                    bmiLabel = getString(R.string.very_severely_underweight);
                } else if (Float.compare(Float.parseFloat(curbmi), 15f) > 0  &&  Float.compare(Float.parseFloat(curbmi), 16f) <= 0) {
                    bmiLabel = getString(R.string.severely_underweight);
                } else if (Float.compare(Float.parseFloat(curbmi), 16f) > 0  &&  Float.compare(Float.parseFloat(curbmi), 18.5f) <= 0) {
                    bmiLabel = getString(R.string.underweight);
                } else if (Float.compare(Float.parseFloat(curbmi), 18.5f) > 0  &&  Float.compare(Float.parseFloat(curbmi), 25f) <= 0) {
                    bmiLabel = getString(R.string.normal);
                } else if (Float.compare(Float.parseFloat(curbmi), 25f) > 0  &&  Float.compare(Float.parseFloat(curbmi), 30f) <= 0) {
                    bmiLabel = getString(R.string.overweight);
                } else if (Float.compare(Float.parseFloat(curbmi), 30f) > 0  &&  Float.compare(Float.parseFloat(curbmi), 35f) <= 0) {
                    bmiLabel = getString(R.string.obese_class_i);
                } else if (Float.compare(Float.parseFloat(curbmi), 35f) > 0  &&  Float.compare(Float.parseFloat(curbmi), 40f) <= 0) {
                    bmiLabel = getString(R.string.obese_class_ii);
                } else {
                    bmiLabel = getString(R.string.obese_class_iii);
                }

                bmiLabel = "Based on your current BMI, you are " + bmiLabel;
                BMIlabel.setText(bmiLabel);

                String calorie = dataSnapshot.child("calorieIntake").getValue().toString();
                if (calorie.matches("")){
                    calorieintake.setText("");
                }
                else {
                    calorieintake.setText("Your daily calorie intake would be: " + calorie +"/day");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, AccountActivity.class));

                break;

            case R.id.menuUpdateBMI:

                Intent intent = new Intent(this,UpdateBMI.class);
                intent.putExtra("curbmi",curbmi); //passing current bmi value to the intent
                startActivity(intent);

                break;

            case R.id.menuCalorieCounter:

                startActivity(new Intent(this,CalorieCounter.class));

                break;
        }
        return true;
    }
}
