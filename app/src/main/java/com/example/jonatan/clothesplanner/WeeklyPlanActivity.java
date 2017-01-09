package com.example.jonatan.clothesplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;

import java.util.List;

public class WeeklyPlanActivity extends AppCompatActivity {

    private IWardrobe wardrobe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_plan);

        wardrobe = Wardrobe.getInstance();
        LinearLayout weeklyPlanLinearLayout = (LinearLayout) findViewById(R.id.weekly_plan_layout);
        List<Trousers> trousers = wardrobe.getTrousers();
        List<Shirt> shirts = wardrobe.getShirts();

        for (int i = 0; i < trousers.size(); i++)
        {
           weeklyPlanLinearLayout.addView(trousers.get(i).getView(this));
        }
    }
}
