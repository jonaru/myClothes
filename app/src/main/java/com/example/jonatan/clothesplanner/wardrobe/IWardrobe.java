package com.example.jonatan.clothesplanner.wardrobe;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.MainActivity;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

/**
 * Created by Jonatan on 2016-12-13.
 */

public interface IWardrobe {
    void addWardrobeItem(EditText item, LinearLayout wardrobeItemsLinearLayout, AppCompatActivity activity);

    IWardrobeItem findWardrobeItem(@SuppressWarnings("SameParameterValue") String wardrobeItemString);

    void addWardrobeItem(IWardrobeItem wardrobeItem);
}
