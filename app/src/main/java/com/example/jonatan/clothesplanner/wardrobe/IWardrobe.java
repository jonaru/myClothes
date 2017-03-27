package com.example.jonatan.clothesplanner.wardrobe;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

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
