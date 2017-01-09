package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

import android.content.Context;
import android.view.View;

/**
 * Created by Jonatan on 2017-01-09.
 */
public class Trousers extends WardrobeItem {

    private final String trousers;

    public Trousers(@SuppressWarnings("SameParameterValue") String trousersString) {
        //noinspection RedundantStringConstructorCall
        trousers = new String(trousersString);
    }

    @Override
    public String getWardrobeItemString() {
        return trousers;
    }

    @Override
    public WardrobeItemType getWardrobeItemType() {
        return WardrobeItemType.TROUSERS;
    }
}
