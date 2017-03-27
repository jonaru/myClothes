package com.example.jonatan.clothesplanner.wardrobe;

import android.view.View;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

import java.util.List;

/**
 * Created by Jonatan on 2017-03-27.
 */
public class WeeklyPlan implements IWeeklyPlan {
    private int weeklyPlanShirtIndex = 0;
    private int weeklyPlanTrousersIndex = 0;
    private boolean isEmpty;
    IWardrobe wardrobe = Wardrobe.getInstance();

    public WeeklyPlan()
    {
        isEmpty = true;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public void storeWeeklyPlan() {
        return;
    }


    @Override
    public IWardrobeItem getShirt() {
        if (weeklyPlanShirtIndex > wardrobe.getShirts().size())
        {
            weeklyPlanShirtIndex = 0;
        }

        IWardrobeItem shirt = wardrobe.getShirts().get((weeklyPlanShirtIndex));
        weeklyPlanShirtIndex++;
        return shirt;
    }

    @Override
    public IWardrobeItem getTrousers() {
        if (weeklyPlanTrousersIndex > wardrobe.getTrousers().size())
        {
            weeklyPlanTrousersIndex = 0;
        }

        IWardrobeItem trousers = wardrobe.getTrousers().get((weeklyPlanTrousersIndex));
        weeklyPlanTrousersIndex++;
        return trousers;
    }


    @Override
    public void generateWeeklyPlan() {
        isEmpty = false;
    }
}
