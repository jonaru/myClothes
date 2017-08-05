package com.example.jonatan.clothesplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.IWeeklyPlan;
import com.example.jonatan.clothesplanner.wardrobe.WeeklyPlan;

import java.util.ArrayList;
import java.util.List;

public class WeeklyPlanActivity extends AppCompatActivity {

    private IWardrobe wardrobe;
    private IWeeklyPlan weeklyPlan;
    List<LinearLayout> dailyPlans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_plan);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        wardrobe = Wardrobe.getInstance();
        weeklyPlan = new WeeklyPlan();

        dailyPlans.add((LinearLayout) findViewById(R.id.mondayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.tuesdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.wednesdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.thursdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.fridayViewGroup));

        displayWeeklyPlan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_toolbar, menu);
        return true;
    }

    public void generatePlan(@SuppressWarnings("UnusedParameters") View view) {
        weeklyPlan.generateWeeklyPlan();
        displayWeeklyPlan();
    }

    public void displayWeeklyPlan() {

        for (LinearLayout day : dailyPlans)
        {
            day.removeAllViews();
        }

        if (wardrobe.getUpperItems().size() > 0) {
            for (int i = 0; i < dailyPlans.size(); i++)
            {
                weeklyPlan.getShirt().addToView(dailyPlans.get(i));
                dailyPlans.get(i).setVisibility(View.VISIBLE);
            }
        }

        if (wardrobe.getLowerItems().size() > 0) {
            for (int i = 0; i < dailyPlans.size(); i++)
            {
                weeklyPlan.getTrousers().addToView(dailyPlans.get(i));
                dailyPlans.get(i).setVisibility(View.VISIBLE);
            }
        }
    }
}
