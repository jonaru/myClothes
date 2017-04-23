package com.example.jonatan.clothesplanner.wardrobe.filehandling;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;

/**
 * Created by Jonatan on 2017-01-28.
 */

public interface IFileHandlingHelper {
    void loadWardrobe(IWardrobe wardrobe);
    void storeWardrobe(IWardrobe wardrobe);

    int[] loadWeeklyPlanIndex();

    void storeWeeklyPlanIndex(int weeklyPlanShirtIndex, int weeklyPlanTrousersIndex);
}
