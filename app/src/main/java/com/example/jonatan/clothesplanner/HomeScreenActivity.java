package com.example.jonatan.clothesplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jonatan.clothesplanner.wardrobe.FileHandlingHelper;
import com.example.jonatan.clothesplanner.wardrobe.IFileHandlingHelper;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Initialize Wardrobe
        Wardrobe.initInstance(getApplicationContext());
        Wardrobe.getInstance().loadWardrobe();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void goToWardrobe(View view) {
        Intent intent = new Intent(this, WardrobeActivity.class);
        startActivity(intent);
    }

    public void goToWeeklyPlan(View view) {
        Intent intent = new Intent(this, WeeklyPlanActivity.class);
        startActivity(intent);
    }
}
