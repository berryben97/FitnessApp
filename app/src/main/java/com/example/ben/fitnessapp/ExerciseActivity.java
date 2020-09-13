package com.example.ben.fitnessapp;

import android.content.Intent;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ben.fitnessapp.Model.Exercises;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    //Firebase
    private DatabaseReference database;
    private DatabaseReference database2;
    private DatabaseReference database3;

    // Android layout
    private ListView listView;

    //Array list
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,R.layout.listlayout,R.id.textView1,arrayList);

        //getting the passed choice result.
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String Parts = extras.getString("Parts");
        final String Choices = extras.getString("Choice");

/*        Toast.makeText(this, "Please work: " + Parts +" and " + Choices, Toast.LENGTH_SHORT).show();*/

        //pointing to the database with the passed value
        database = FirebaseDatabase.getInstance().getReference("Exercises");
        database2 = database.child(Parts);
        database3 = database2.child(Choices);

        listView = (ListView) findViewById(R.id.listView);

        //retrieving value from firebase
        database3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    String test = ds.getKey(); //getKey is used for getting the parent node only
                    arrayList.add(test); //setting the value into the array

                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //used to get position id of the item clicked in the listview.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(ExerciseActivity.this, "You clicked " + arrayList.get(position), Toast.LENGTH_SHORT).show();*/

                //declare position into string so that it can be passed
                String exercise = arrayList.get(position);

                Intent intenta = new Intent(ExerciseActivity.this, DetailsOfExercises.class);
                Bundle extras = new Bundle();
                extras.putString("exercise",exercise);
                extras.putString("parts", Parts);
                extras.putString("choice", Choices);
                intenta.putExtras(extras);
                startActivity(intenta);

            }
        });
    }
}
