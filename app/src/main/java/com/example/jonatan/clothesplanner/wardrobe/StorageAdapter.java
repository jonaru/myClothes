package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;

/**
 * Created by Jonatan on 2017-04-23.
 */

public class StorageAdapter implements IStorageAdapter {
    private IStorageHelper storageHelper;

    public StorageAdapter(IStorageHelper storage)
    {
        storageHelper = storage;
    }

    @Override
    public void loadWardrobe(IWardrobe wardrobe) {
        storageHelper.loadWardrobe(wardrobe);
    }

    @Override
    public void storeWardrobe(IWardrobe wardrobe) {
        storageHelper.storeWardrobe(wardrobe);
    }

    @Override
    public int[] loadWeeklyPlanIndex() {
        return storageHelper.loadWeeklyPlanIndex();
    }

    @Override
    public void storeWeeklyPlanIndex(int weeklyPlanShirtIndex, int weeklyPlanTrousersIndex) {
        storageHelper.storeWeeklyPlanIndex(weeklyPlanShirtIndex, weeklyPlanTrousersIndex);
    }

    @Override
    public void setStorageHelper(IStorageHelper storageHelper) {
        this.storageHelper = storageHelper;
    }

    @Override
    public void closeStorage() {
        storageHelper.closeStorage();
    }
}
