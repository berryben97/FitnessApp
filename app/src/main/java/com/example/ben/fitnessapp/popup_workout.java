package com.example.ben.fitnessapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class popup_workout extends Activity {

    DatabaseReference database1;
    DatabaseReference database2;
    DatabaseReference database3;
    DatabaseReference database4;

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
        setContentView(R.layout.popup_workout);

        //setting the popup window to fit the phone size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        //this set of codes is used to dim the background of the popup activity
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount=0.5f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //setting image as the imageview in the design (using id)
        imageview = (ImageView)findViewById(R.id.image);

        //setting textview
        title = (TextView)findViewById(R.id.title);
        primary = (TextView)findViewById(R.id.primary);
        secondary = (TextView)findViewById(R.id.secondary);
        instruction = (TextView)findViewById(R.id.instruction);

        //setting instruction textview to be scrollable
        instruction.setMovementMethod(new ScrollingMovementMethod());

        Bundle choice = getIntent().getExtras();
        String name = choice.getString("name");
        String type = choice.getString("type");
        String category = choice.getString("category");

        title.setText(name);
        primary.setText(type);

        /*Toast.makeText(this, ""+ choices, Toast.LENGTH_SHORT).show();*/

        database1 = FirebaseDatabase.getInstance().getReference("Exercises");
        database2 = database1.child(type);
        database3 = database2.child(category);
        database4 = database3.child(name);

        /*Toast.makeText(this, ""+ type + category + name, Toast.LENGTH_SHORT).show();*/
        database4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = dataSnapshot.child("image").getValue().toString();//get the url from firebase.
                /*Toast.makeText(popup_workout.this, "this is the url " + url, Toast.LENGTH_SHORT).show();*/
                Glide.with(popup_workout.this).load(url).into(imageview); //Glide is used to print out GIF

                String secondarymuscle = dataSnapshot.child("secondary").getValue().toString();//getting the string from firebase.
                secondary.setText(secondarymuscle);

                String instrusctions = dataSnapshot.child("instructions").getValue().toString();//getting instructions from firebase.
                instruction.setText(instrusctions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
