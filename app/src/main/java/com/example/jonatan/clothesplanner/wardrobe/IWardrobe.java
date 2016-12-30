package com.example.jonatan.clothesplanner.wardrobe;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.MainActivity;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;

import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */

public interface IWardrobe {
    void addWardrobeItem(String itemString);

    IWardrobeItem findWardrobeItem(@SuppressWarnings("SameParameterValue") String wardrobeItemString);

    void addWardrobeItem(IWardrobeItem wardrobeItem);

    List<Shirt> getShirts();
}
