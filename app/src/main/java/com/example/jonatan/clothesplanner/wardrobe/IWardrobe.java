package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Jonatan on 2016-12-13.
 */

public interface IWardrobe {
    void addWardrobeItem(EditText item);

    IWardrobeItem findWardrobeItem(String wardrobeItemString);

    void addWardrobeItem(IWardrobeItem wardrobeItem);
}
