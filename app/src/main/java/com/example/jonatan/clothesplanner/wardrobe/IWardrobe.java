package com.example.jonatan.clothesplanner.wardrobe;

import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.MainActivity;

/**
 * Created by Jonatan on 2016-12-13.
 */

public interface IWardrobe {
    void addWardrobeItem(EditText item, LinearLayout wardrobeItemsLinearLayout, MainActivity mainActivity);

    IWardrobeItem findWardrobeItem(String wardrobeItemString);

    void addWardrobeItem(IWardrobeItem wardrobeItem);
}
