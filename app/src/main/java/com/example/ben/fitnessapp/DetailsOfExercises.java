package com.example.ben.fitnessapp;

import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ben.fitnessapp.Model.Exercises;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailsOfExercises extends AppCompatActivity {

    //Firebase
    private DatabaseReference database;
    private DatabaseReference database2;
    private DatabaseReference database3;
    private DatabaseReference database4;

    //imageview
    ImageView imageview;

    //textview
    TextView title;
    TextView primary;
    TextView secondary;
    TextView instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_exercises);

        String url;
        //set the model for it to get image url
        final Exercises exercise = new Exercises();

        //setting image as the imageview in the design (using id)
        imageview = (ImageView)findViewById(R.id.image);

        //setting textview
        title = (TextView)findViewById(R.id.title);
        primary = (TextView)findViewById(R.id.primary);
        secondary = (TextView)findViewById(R.id.secondary);
        instruction = (TextView)findViewById(R.id.instruction);

        //setting instruction textview to be scrollable
        instruction.setMovementMethod(new ScrollingMovementMethod());

        //received passed value from ExerciseActivity
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String Parts = extras.getString("parts");
        final String Choices = extras.getString("choice");
        final String Exercise = extras.getString("exercise");

        //setting the title name from the choice selected and passed to this activity
        title.setText(Exercise);
        primary.setText(Parts);

        /*Toast.makeText(this, "Please work: "+ Parts + " " + Choices + " " + Exercise, Toast.LENGTH_SHORT).show();*/
        //point to firebase database
        database = FirebaseDatabase.getInstance().getReference("Exercises");
        database2 = database.child(Parts);
        database3 = database2.child(Choices);
        database4 = database3.child(Exercise);

        database4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String url = dataSnapshot.child("image").getValue().toString();//get the url from firebase.
                    /*Toast.makeText(DetailsOfExercises.this, "this is the url " + url, Toast.LENGTH_SHORT).show();*/
                    Glide.with(DetailsOfExercises.this).load(url).into(imageview); //Glide is used to print out GIF

                    String secondarymuscle = dataSnapshot.child("secondary").getValue().toString();//getting the string from firebase.
                    secondary.setText(secondarymuscle)  ;

                    String instrusctions = dataSnapshot.child("instructions").getValue().toString();//getting instructions from firebase.
                    instruction.setText(instrusctions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
