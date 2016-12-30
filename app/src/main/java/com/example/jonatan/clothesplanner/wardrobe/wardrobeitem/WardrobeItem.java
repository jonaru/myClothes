package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class WardrobeItem implements IWardrobeItem {
    private final String wardrobeItemString;

    public WardrobeItem(String wardrobeItemInputString) {
        //noinspection RedundantStringConstructorCall
        wardrobeItemString = new String(wardrobeItemInputString);
    }

    @Override
    public String getWardrobeItemString() {
        return wardrobeItemString;
    }

    @Override
    public WardrobeItemType getWardrobeItemType() {
        return WardrobeItemType.DEFAULT;
    }
}
