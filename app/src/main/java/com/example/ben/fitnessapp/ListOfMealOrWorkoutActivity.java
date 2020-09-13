package com.example.ben.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ben.fitnessapp.Model.NameAndImage;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListOfMealOrWorkoutActivity extends AppCompatActivity {

    //Listview
    private ListView listView;

    //adapter
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_meal_or_workout);

        //to recieve string passed from PlannerActivity
        Bundle choice = getIntent().getExtras();
        final String choices = choice.getString("choice");

        /*Toast.makeText(this, "chose: " + choices, Toast.LENGTH_SHORT).show();*/

        listView = (ListView)findViewById(R.id.listviewplanner); //setting listview

        assert choices != null; //making sure the values passed is not null
        Query query = FirebaseDatabase.getInstance().getReference().child(choices); //pointing to the database that is selected
        final FirebaseListOptions<NameAndImage> options = new FirebaseListOptions.Builder<NameAndImage>()
                .setLayout(R.layout.listlayout2)
                .setQuery(query,NameAndImage.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                //setting the imageview and textview in listlayout2
                TextView names = v.findViewById(R.id.textView1);
                ImageView images = v.findViewById(R.id.imageview);

                NameAndImage nai = (NameAndImage) model;

                //set text and images into the layout
                names.setText(nai.getName());
                Picasso.get().load(nai.getImage()).into(images);
            }
        };
        listView.setAdapter(adapter);

        if (choices.equals("Meal")){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DatabaseReference itemRef = adapter.getRef(position); //used to get position of the item clicked
                    String itemchoice = itemRef.getKey();//used to translate the Referrence got into String
                    /*Toast.makeText(ListOfMealOrWorkoutActivity.this, "You Clicked on " + itemchoice, Toast.LENGTH_SHORT).show();*/

                    //passing items clicked to other activity
                    Intent intenta = new Intent(ListOfMealOrWorkoutActivity.this,MealActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("choice",choices);
                    extras.putString("list",itemchoice);
                    intenta.putExtras(extras);
                    startActivity(intenta);
                }
            });
        }
        else
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DatabaseReference itemRef = adapter.getRef(position);//used to get position of the item clicked
                    String itemchoice = itemRef.getKey();//used to translate the Referrence got into String
                    /*Toast.makeText(ListOfMealOrWorkoutActivity.this, "You Clicked on "+ itemchoice, Toast.LENGTH_SHORT).show();*/

                    //passing items clicked to other activity
                    Intent intenta = new Intent(ListOfMealOrWorkoutActivity.this,WorkoutActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("choice",choices);
                    extras.putString("list",itemchoice);
                    intenta.putExtras(extras);
                    startActivity(intenta);
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
