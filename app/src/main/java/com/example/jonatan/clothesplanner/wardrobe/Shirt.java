package com.example.jonatan.clothesplanner.wardrobe;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobeItem;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Shirt implements IWardrobeItem {

    private final String shirt;

    public Shirt(@SuppressWarnings("SameParameterValue") String shirtString) {
        //noinspection RedundantStringConstructorCall
        shirt = new String(shirtString);
    }

    @Override
    public String getWardrobeItemString() {
        return shirt;
    }
}
