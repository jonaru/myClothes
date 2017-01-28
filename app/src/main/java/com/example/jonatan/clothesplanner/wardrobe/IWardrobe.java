package com.example.jonatan.clothesplanner.wardrobe;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.MainActivity;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;

import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */

public interface IWardrobe {

    IWardrobeItem findWardrobeItem(@SuppressWarnings("SameParameterValue") String wardrobeItemString);

    IWardrobeItem addWardrobeItem(String itemString, String itemTypeString);

    void addWardrobeItem(IWardrobeItem wardrobeItem);

    List<IWardrobeItem> getShirts();

    List<IWardrobeItem> getTrousers();

    void clear();

    boolean removeWardrobeItem(String wardrobeItemString);

    void loadWardrobe();

    void storeWardrobe();
}
