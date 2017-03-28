package com.example.jonatan.clothesplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.IWeeklyPlan;
import com.example.jonatan.clothesplanner.wardrobe.WeeklyPlan;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

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

        wardrobe = Wardrobe.getInstance();
        weeklyPlan = new WeeklyPlan();

        dailyPlans.add((LinearLayout) findViewById(R.id.mondayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.tuesdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.wednesdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.thursdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.fridayViewGroup));

        if (!weeklyPlan.isEmpty())
        {
            displayWeeklyPlan();
        }
    }

    public void displayWeeklyPlan() {
        //LinearLayout weeklyPlanLinearLayout = (LinearLayout) findViewById(R.id.weekly_plan_layout);
        List<IWardrobeItem> trousers = wardrobe.getTrousers();
        List<IWardrobeItem> shirts = wardrobe.getShirts();

        for (int i = 0; i < trousers.size() && i < this.dailyPlans.size(); i++)
        {
            dailyPlans.get(i).addView(weeklyPlan.getTrousers().getView(this));
            dailyPlans.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < shirts.size() && i < this.dailyPlans.size(); i++)
        {
            dailyPlans.get(i).addView(weeklyPlan.getShirt().getView(this));
            dailyPlans.get(i).setVisibility(View.VISIBLE);
        }
    }

    public void generatePlan(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        weeklyPlan.generateWeeklyPlan();
        displayWeeklyPlan();
    }
}
