package com.example.jonatan.clothesplanner.wardrobe;

import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

import java.util.List;

/**
 * Created by Jonatan on 2017-03-27.
 */
public interface IWeeklyPlan {
    void storeWeeklyPlan();
    void generateWeeklyPlan();

    IWardrobeItem getShirt();
    IWardrobeItem getTrousers();
}
