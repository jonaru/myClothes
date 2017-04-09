package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

import android.graphics.drawable.Drawable;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Shirt extends WardrobeItem {

    public Shirt(String description) {
        //noinspection RedundantStringConstructorCall
        itemDescription = new String(description);
    }

    public Shirt(String description, Drawable selectedDrawable) {
        itemDescription = new String(description);
        drawable = selectedDrawable;
    }

    @Override
    public WardrobeItemType getWardrobeItemType() {
        return WardrobeItemType.SHIRT;
    }
}
