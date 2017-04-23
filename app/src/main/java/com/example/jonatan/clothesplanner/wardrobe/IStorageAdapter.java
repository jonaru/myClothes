package com.example.jonatan.clothesplanner.wardrobe;

/**
 * Created by Jonatan on 2017-04-23.
 */

interface IStorageAdapter {
    void loadWardrobe(IWardrobe wardrobe);
    void storeWardrobe(IWardrobe wardrobe);

    int[] loadWeeklyPlanIndex();

    void storeWeeklyPlanIndex(int weeklyPlanShirtIndex, int weeklyPlanTrousersIndex);

    void setStorageHelper(IStorageHelper storageHelper);
}
