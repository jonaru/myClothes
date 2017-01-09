package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

import android.content.Context;
import android.view.View;

/**
 * Created by Jonatan on 2016-12-13.
 */
public interface IWardrobeItem {
    String getWardrobeItemString();

    WardrobeItemType getWardrobeItemType();

    View getView(Context context);
}
