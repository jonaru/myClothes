package com.example.jonatan.clothesplanner.wardrobe;

import android.graphics.drawable.Drawable;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

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

    boolean removeWardrobeItem(Drawable drawable);

    void loadWardrobe();

    void storeWardrobe();

    IFileHandlingHelper getFileHandlingHelper();

    List<IWardrobeItem> getTempShirts();

    List<IWardrobeItem> getTempTrousers();

    void clearTempLists();

    IWardrobeItem addTempWardrobeItem(String itemText, String itemTypeString);

    void addTempWardrobeItem(String itemText, WardrobeItemType selectedItemType, Drawable selectedDrawable);
}
