package com.example.ben.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ben.fitnessapp.Model.WorkoutList;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class WorkoutActivity extends AppCompatActivity {

    //Listview
    private ListView listView;

    //adapter
    FirebaseListAdapter adapter;

    //imageview
    ImageView imageview;

    //textview
    TextView Title;

    //querry
    Query query1;
    Query query2;
    Query query3;

    //database reference
    DatabaseReference database;
    DatabaseReference database2;
    DatabaseReference database3;
    DatabaseReference database4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        //retrieving the passed value from ListOfMealOrWorkoutActivity
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String choices = extras.getString("choice");
        final String item = extras.getString("list");

        /*Toast.makeText(this, "Work please " +choices + item , Toast.LENGTH_SHORT).show();*/ //to check passed items are correct

        //setting imageview
        imageview = (ImageView) findViewById(R.id.imageviewworkout);

        //setting textviews
        Title = (TextView)findViewById(R.id.TitleWorkout);

        //setting the title name
        Title.setText(item);

        //setting the listview
        listView = (ListView)findViewById(R.id.workoutlist);

        //setting the database to point at the correct place
        database = FirebaseDatabase.getInstance().getReference("Workout");
        database2 = database.child(item);
        database3 = database2.child("Exercises");

        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = dataSnapshot.child("image").getValue().toString();//get the url from firebase.
                Picasso.get().load(url).into(imageview);//loading the image into imageviewer with picasso
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query1 = FirebaseDatabase.getInstance().getReference("Workout");
        query2 = ((DatabaseReference) query1).child(item);
        query3 = ((DatabaseReference) query2).child("Exercises");

        final FirebaseListOptions<WorkoutList> options = new FirebaseListOptions.Builder<WorkoutList>()
                .setLayout(R.layout.workoutlistlayout)
                .setQuery(query3,WorkoutList.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                //setting imageview and textview
                TextView names = v.findViewById(R.id.exercisename);
                TextView noreps = v.findViewById(R.id.numberofreps);
                ImageView workoutgif = v.findViewById(R.id.exercisegif);

                WorkoutList wl = (WorkoutList) model;

                names.setText(wl.getName());
                noreps.setText(wl.getRep());
                Glide.with(WorkoutActivity.this).load(wl.getGif()).into(workoutgif);

            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference itemRef = adapter.getRef(position); //used to get position of the item clicked
                String itemchoice = itemRef.getKey();//used to get the Key name
                /*Toast.makeText(WorkoutActivity.this, "You clicked on " + itemchoice, Toast.LENGTH_SHORT).show();*/

                database4 = database3.child(itemchoice);

                database4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String category = dataSnapshot.child("category").getValue().toString();
                        String type = dataSnapshot.child("type").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();

                        Intent intent = new Intent(WorkoutActivity.this,popup_workout.class);
                        Bundle extras = new Bundle();
                        extras.putString("name",name);
                        extras.putString("type",type);
                        extras.putString("category",category);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
