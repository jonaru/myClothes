package com.example.jonatan.clothesplanner.wardrobe;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Wardrobe implements IWardrobe {
    private final List<IWardrobeItem> wardrobeItemList = new ArrayList<>();
    private final ArrayList<Shirt> shirtList = new ArrayList<>();

    public Wardrobe ()
    {
    }


    @Override
    public void addWardrobeItem(String itemString) {
        IWardrobeItem itemToAdd = new WardrobeItem(itemString);
        addWardrobeItem(itemToAdd);
    }

    @Override
    public void addWardrobeItem(IWardrobeItem wardrobeItem) {
        switch (wardrobeItem.getWardrobeItemType()){
            case SHIRT: shirtList.add((Shirt)wardrobeItem); break;
            default: wardrobeItemList.add(0, wardrobeItem); break;
        }
    }

    @Override
    public List<Shirt> getShirts() {
        return shirtList;
    }

    @Override
    public IWardrobeItem findWardrobeItem(String wardrobeItemString) {
        for (IWardrobeItem item : shirtList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                return item;
            }
        }

        for (IWardrobeItem item : wardrobeItemList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                return item;
            }
        }
        return null;
    }
}
