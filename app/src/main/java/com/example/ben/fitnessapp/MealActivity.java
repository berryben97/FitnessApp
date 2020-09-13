package com.example.ben.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MealActivity extends AppCompatActivity {

    //database referrence
    DatabaseReference database;
    DatabaseReference database2;

    //imageview
    ImageView imageview;

    //textview
    TextView title;
    TextView description;
    TextView thingstoeat;
    TextView thingstoavoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        //setting imageview
        imageview = (ImageView)findViewById(R.id.imageview);

        //setting textview
        title = (TextView)findViewById(R.id.TitleMeal);
        description = (TextView)findViewById(R.id.MealDescription);
        thingstoeat = (TextView)findViewById(R.id.ThingsToEat);
        thingstoavoid = (TextView)findViewById(R.id.ThingsToAvoid);

        //retrieving the passed value from ListOfMealOrWorkoutActivity
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String choices = extras.getString("choice");
        final String item = extras.getString("list");
        /*Toast.makeText(this, "work please "+choices + item, Toast.LENGTH_SHORT).show();*/

        title.setText(item);

        //setting instruction textview to be scrollable
        description.setMovementMethod(new ScrollingMovementMethod());
        thingstoeat.setMovementMethod(new ScrollingMovementMethod());
        thingstoavoid.setMovementMethod(new ScrollingMovementMethod());

        //pointing to database
        database = FirebaseDatabase.getInstance().getReference(choices);
        database2 = database.child(item);

        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = dataSnapshot.child("image").getValue().toString(); //getting the image url
                /*Toast.makeText(MealActivity.this, ""+url, Toast.LENGTH_SHORT).show();*/
                Picasso.get().load(url).into(imageview);//loading the image into imageviewer with picasso

                String descriptions = dataSnapshot.child("description").getValue().toString();
                description.setText(descriptions);

                String eat = dataSnapshot.child("thingstoeat").getValue().toString();
                thingstoeat.setText(eat);

                String avoid = dataSnapshot.child("thingstoavoid").getValue().toString();
                thingstoavoid.setText(avoid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
