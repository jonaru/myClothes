package com.example.jonatan.clothesplanner.wardrobe;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Wardrobe implements IWardrobe {
    private static final String SHIRT_STRING = "Shirt";
    private static final String TROUSERS_STRING = "Trousers";
    private final List<IWardrobeItem> wardrobeItemList = new ArrayList<>();
    private final ArrayList<Shirt> shirtList = new ArrayList<>();
    private final ArrayList<Trousers> trousersList = new ArrayList<>();
    private static Wardrobe instance;

    private Wardrobe ()
    {
    }

    public static void initInstance() {
        if (instance == null)
        {
            instance = new Wardrobe();
        }
    }

    public static Wardrobe getInstance() {
        return instance;
    }


    @Override
    public void addWardrobeItem(String itemString, String itemTypeString) {
        if (itemTypeString.compareTo(SHIRT_STRING) == 0)
        {
            addWardrobeItem(new Shirt(itemString));
        }
        else if (itemTypeString.compareTo(TROUSERS_STRING) == 0)
        {
            addWardrobeItem(new Trousers(itemString));
        }
    }

    @Override
    public void addWardrobeItem(IWardrobeItem wardrobeItem) {
        switch (wardrobeItem.getWardrobeItemType()){
            case SHIRT: shirtList.add((Shirt)wardrobeItem); break;
            case TROUSERS: trousersList.add((Trousers)wardrobeItem); break;
            default: wardrobeItemList.add(wardrobeItem); break;
        }
    }

    @Override
    public List<Shirt> getShirts() {
        return shirtList;
    }

    @Override
    public List<Trousers> getTrousers() { return trousersList; }

    @Override
    public void clear() {
        shirtList.clear();
        trousersList.clear();

        //TODO: Clear file storage too?
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

        for (IWardrobeItem item : trousersList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                return item;
            }
        }
        return null;
    }
}
