package com.example.jonatan.clothesplanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.IWeeklyPlan;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.WeeklyPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2017-08-23.
 */

public class WeeklyPlanFragment extends Fragment implements View.OnClickListener {
    private IWardrobe wardrobe;
    private IWeeklyPlan weeklyPlan;
    List<LinearLayout> dailyPlans = new ArrayList<>();

    public WeeklyPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wardrobe = Wardrobe.getInstance();
        weeklyPlan = new WeeklyPlan();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dailyPlans.add((LinearLayout) getActivity().findViewById(R.id.mondayViewGroup));
        dailyPlans.add((LinearLayout) getActivity().findViewById(R.id.tuesdayViewGroup));
        dailyPlans.add((LinearLayout) getActivity().findViewById(R.id.wednesdayViewGroup));
        dailyPlans.add((LinearLayout) getActivity().findViewById(R.id.thursdayViewGroup));
        dailyPlans.add((LinearLayout) getActivity().findViewById(R.id.fridayViewGroup));

        Button addItemButton = (Button) getActivity().findViewById(R.id.GenerateWeeklyPlanButton);
        addItemButton.setOnClickListener(this);

        displayWeeklyPlan();
    }

    public void onClick(@SuppressWarnings("UnusedParameters") View view) {
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
