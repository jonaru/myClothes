package com.example.jonatan.clothesplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;

import java.util.ArrayList;
import java.util.List;

public class WeeklyPlanActivity extends AppCompatActivity {

    private IWardrobe wardrobe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_plan);
    }

    public void generatePlan(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        wardrobe = Wardrobe.getInstance();
        LinearLayout weeklyPlanLinearLayout = (LinearLayout) findViewById(R.id.weekly_plan_layout);
        List<IWardrobeItem> trousers = wardrobe.getTrousers();
        List<IWardrobeItem> shirts = wardrobe.getShirts();

        List<LinearLayout> dailyPlans = new ArrayList<>();
        dailyPlans.add((LinearLayout) findViewById(R.id.mondayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.tuesdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.wednesdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.thursdayViewGroup));
        dailyPlans.add((LinearLayout) findViewById(R.id.fridayViewGroup));

        for (int i = 0; i < trousers.size(); i++)
        {
            dailyPlans.get(i).addView(trousers.get(i).getView(this));
            dailyPlans.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < shirts.size(); i++)
        {
            dailyPlans.get(i).addView(shirts.get(i).getView(this));
            dailyPlans.get(i).setVisibility(View.VISIBLE);
        }
    }
}
