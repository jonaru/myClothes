package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by Jonatan on 2016-12-13.
 */
public interface IWardrobeItem {
    String getWardrobeItemString();
    Drawable getDrawable();

    WardrobeItemType getWardrobeItemType();

    View getView(Context context);
}
