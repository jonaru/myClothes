package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by Jonatan on 2017-01-09.
 */
public class Trousers extends WardrobeItem {

    public Trousers(String description) {
        //noinspection RedundantStringConstructorCall
        itemDescription = new String(description);
    }

    public Trousers(String description, Drawable selectedDrawable) {
        itemDescription = new String(description);
        drawable = selectedDrawable;
    }

    @Override
    public WardrobeItemType getWardrobeItemType() {
        return WardrobeItemType.TROUSERS;
    }
}
