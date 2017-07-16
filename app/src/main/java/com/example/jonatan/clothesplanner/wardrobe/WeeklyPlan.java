package com.example.jonatan.clothesplanner.wardrobe;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

/**
 * Created by Jonatan on 2017-03-27.
 */
public class WeeklyPlan implements IWeeklyPlan {
    private int currentShirt;
    private int currentTrousers;
    private int weeklyPlanShirtIndex;
    private int weeklyPlanTrousersIndex;

    private IStorageAdapter storageAdapter;
    IWardrobe wardrobe = Wardrobe.getInstance();

    public WeeklyPlan()
    {
        storageAdapter = wardrobe.getStorageAdapter();
        int[] indices = storageAdapter.loadWeeklyPlanIndex();
        weeklyPlanShirtIndex = indices[0];
        weeklyPlanTrousersIndex = indices[1];
        currentShirt = weeklyPlanShirtIndex;
        currentTrousers = weeklyPlanTrousersIndex;
    }

    @Override
    public void storeWeeklyPlan() {
        return;
    }


    @Override
    public IWardrobeItem getShirt() {
        if (wardrobe.getShirts().size() == 0) {
            return null;
        }
        if (currentShirt + 1 > wardrobe.getShirts().size())
        {
            currentShirt = 0;
        }

        IWardrobeItem shirt = wardrobe.getShirts().get((currentShirt));
        currentShirt++;
        return shirt;
    }

    @Override
    public IWardrobeItem getTrousers() {
        if (wardrobe.getTrousers().size() == 0) {
            return null;
        }
        if (currentTrousers + 1 > wardrobe.getTrousers().size())
        {
            currentTrousers = 0;
        }

        IWardrobeItem trousers = wardrobe.getTrousers().get((currentTrousers));
        currentTrousers++;
        return trousers;
    }

    @Override
    public void generateWeeklyPlan() {
        weeklyPlanShirtIndex++;
        if (weeklyPlanShirtIndex >= wardrobe.getShirts().size())
        {
            weeklyPlanShirtIndex = 0;
        }

        currentShirt = weeklyPlanShirtIndex;
        currentTrousers = weeklyPlanTrousersIndex;


        storageAdapter.storeWeeklyPlanIndex(weeklyPlanShirtIndex, weeklyPlanTrousersIndex);

    }
}
