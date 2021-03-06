package com.example.jonatan.clothesplanner.wardrobe;

import android.graphics.drawable.Drawable;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */

public interface IWardrobe {

    List<IWardrobeItem> getUpperItems();

    List<IWardrobeItem> getLowerItems();

    List<IWardrobeItem> getFootwearItems();

    void clear();

    IWardrobeItem addWardrobeItem(String description, String itemTypeString);

    IWardrobeItem addWardrobeItem(String description, WardrobeItemType selectedItemType, Drawable selectedDrawable);

    IWardrobeItem addWardrobeItem(String description, String itemTypeString, Drawable selectedDrawable);

    void addWardrobeItem(IWardrobeItem wardrobeItem);

    boolean removeWardrobeItem(String wardrobeItemString);

    boolean removeWardrobeItem(Drawable drawable);

    IWardrobeItem findWardrobeItem(@SuppressWarnings("SameParameterValue") String wardrobeItemString);

    void loadWardrobe();

    void storeWardrobe();

    WardrobeItemType getItemTypeFromString(String itemTypeString);

    IStorageAdapter getStorageAdapter();
}
