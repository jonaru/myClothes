package com.example.jonatan.clothesplanner.wardrobe;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobeItem;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class WardrobeItem implements IWardrobeItem {
    private final String wardrobeItem;

    public WardrobeItem(String wardrobeItemString) {
        //noinspection RedundantStringConstructorCall
        wardrobeItem = new String(wardrobeItemString);
    }

    @Override
    public String getWardrobeItemString() {
        return wardrobeItem;
    }
}
