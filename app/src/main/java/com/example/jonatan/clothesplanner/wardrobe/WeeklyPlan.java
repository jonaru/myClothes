package com.example.jonatan.clothesplanner.wardrobe;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

/**
 * Created by Jonatan on 2017-03-27.
 */
public class WeeklyPlan implements IWeeklyPlan {
    private int weeklyPlanStartIndex;
    private int weeklyPlanShirtIndex;
    private int weeklyPlanTrousersIndex;
    IWardrobe wardrobe = Wardrobe.getInstance();
    IFileHandlingHelper fileHandlingHelper;

    public WeeklyPlan()
    {
        fileHandlingHelper = wardrobe.getFileHandlingHelper();
        weeklyPlanStartIndex = fileHandlingHelper.loadWeeklyPlanIndex();
        weeklyPlanShirtIndex = weeklyPlanStartIndex;
        weeklyPlanTrousersIndex = weeklyPlanStartIndex;
    }

    @Override
    public void storeWeeklyPlan() {
        return;
    }


    @Override
    public IWardrobeItem getShirt() {
        if (weeklyPlanShirtIndex + 1 > wardrobe.getShirts().size())
        {
            weeklyPlanShirtIndex = 0;
        }

        IWardrobeItem shirt = wardrobe.getShirts().get((weeklyPlanShirtIndex));
        weeklyPlanShirtIndex++;
        return shirt;
    }

    @Override
    public IWardrobeItem getTrousers() {
        if (weeklyPlanTrousersIndex + 1 > wardrobe.getTrousers().size())
        {
            weeklyPlanTrousersIndex = 0;
        }

        IWardrobeItem trousers = wardrobe.getTrousers().get((weeklyPlanTrousersIndex));
        weeklyPlanTrousersIndex++;
        return trousers;
    }

    @Override
    public void generateWeeklyPlan() {
        weeklyPlanStartIndex++;
        weeklyPlanTrousersIndex = weeklyPlanStartIndex;
        weeklyPlanShirtIndex = weeklyPlanStartIndex;
        fileHandlingHelper.storeWeeklyPlanIndex(weeklyPlanStartIndex);
    }
}
