package com.example.ben.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_planner:
                    Intent intent1 = new Intent(MainMenuActivity.this,PlannerActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_account:
                    Intent intent2 = new Intent(MainMenuActivity.this,AccountActivity.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }
    };

    GridView gridview;
    public static String[] osNameList = {
            "Bicep",
            "Chest"
    };
    public static int[] osImages = {
            R.mipmap.bicep,
            R.mipmap.chest
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new GridAdapter(this, osNameList, osImages));
    }

}
