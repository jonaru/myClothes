package com.example.jonatan.clothesplanner.wardrobe;

import android.graphics.drawable.Drawable;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Wardrobe implements IWardrobe {
    public static final String UPPER_ITEMS_STRING = "upper";
    public static final String LOWER_ITEMS_STRING = "lower";
    private final ArrayList<IWardrobeItem> upperItemsList = new ArrayList<>();
    private final ArrayList<IWardrobeItem> lowerItemsList = new ArrayList<>();
    private static Wardrobe instance;
    private IStorageAdapter storageAdapter;

    private Wardrobe(IStorageAdapter storageAdapter)
    {
        this.storageAdapter = storageAdapter;
    }

    public static void initInstance(IStorageAdapter storageAdapter) {
        if (instance == null)
        {
            instance = new Wardrobe(storageAdapter);
        }
    }

    public static Wardrobe getInstance() {
        return instance;
    }

    @Override
    public List<IWardrobeItem> getUpperItems() {
        return upperItemsList;
    }

    @Override
    public List<IWardrobeItem> getLowerItems() {
        return lowerItemsList;
    }

    @Override
    public void clear() {
        upperItemsList.clear();
        lowerItemsList.clear();
    }

    @Override
    public void loadWardrobe() {
        clear();
        storageAdapter.loadWardrobe(this);
    }

    @Override
    public void storeWardrobe() {
        storageAdapter.storeWardrobe(this);
    }

    public IStorageAdapter getStorageAdapter() {
        return storageAdapter;
    }

    @Override
    public IWardrobeItem addWardrobeItem(String itemString, String itemTypeString) {
        WardrobeItemType type = getItemTypeFromString(itemTypeString);
        IWardrobeItem itemToAdd = new WardrobeItem(itemString, type);
        addWardrobeItem(itemToAdd);
        return itemToAdd;
    }

    @Override
    public IWardrobeItem addWardrobeItem(String description, WardrobeItemType selectedItemType, Drawable selectedDrawable) {
        IWardrobeItem itemToAdd = new WardrobeItem(description, selectedDrawable, selectedItemType);
        addWardrobeItem(itemToAdd);
        return itemToAdd;
    }

    @Override
    public IWardrobeItem addWardrobeItem(String description, String itemTypeString, Drawable selectedDrawable) {
        WardrobeItemType type = getItemTypeFromString(itemTypeString);
        IWardrobeItem itemToAdd = new WardrobeItem(description, selectedDrawable, type);
        addWardrobeItem(itemToAdd);
        return itemToAdd;
    }

    @Override
    public void addWardrobeItem(IWardrobeItem wardrobeItem) {
        switch (wardrobeItem.getWardrobeItemType()){
            case UPPER: upperItemsList.add(wardrobeItem); break;
            case LOWER: lowerItemsList.add(wardrobeItem); break;
        }
    }

    @Override
    public boolean removeWardrobeItem(String wardrobeItemString) {
        for (IWardrobeItem item : upperItemsList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                upperItemsList.remove(item);
                return true;
            }
        }

        for (IWardrobeItem item : lowerItemsList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                lowerItemsList.remove(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeWardrobeItem(Drawable drawable) {
        for (IWardrobeItem item : upperItemsList)
        {
            if (item.getDrawable() == drawable)
            {
                upperItemsList.remove(item);
                return true;
            }
        }

        for (IWardrobeItem item : lowerItemsList)
        {
            if (item.getDrawable() == drawable)
            {
                lowerItemsList.remove(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public IWardrobeItem findWardrobeItem(String wardrobeItemString) {
        for (IWardrobeItem item : upperItemsList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                return item;
            }
        }

        for (IWardrobeItem item : lowerItemsList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                return item;
            }
        }
        return null;
    }

    public WardrobeItemType getItemTypeFromString(String itemTypeString) {
        if (itemTypeString.compareTo(UPPER_ITEMS_STRING) == 0)
        {
            return WardrobeItemType.UPPER;
        }
        else if (itemTypeString.compareTo(LOWER_ITEMS_STRING) == 0)
        {
            return WardrobeItemType.LOWER;
        }
        return WardrobeItemType.DEFAULT;
    }

    //TODO: This crap is used right now for injecting mock into unit test. Should be replaced by better solution
    public void setStorageAdapter(IStorageAdapter storageAdapter) {this.storageAdapter = storageAdapter;}
}
