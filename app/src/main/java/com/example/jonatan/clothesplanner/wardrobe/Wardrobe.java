package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;
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
    public static final String SHIRT_STRING = "Shirt";
    public static final String TROUSERS_STRING = "Trousers";
    private final ArrayList<IWardrobeItem> shirtList = new ArrayList<>();
    private final ArrayList<IWardrobeItem> trousersList = new ArrayList<>();
    private static Wardrobe instance;
    private IStorageAdapter storageAdapter;

    private Wardrobe(IStorageHelper storageHelper)
    {
        storageAdapter = new StorageAdapter(storageHelper);
    }

    public static void initInstance(IStorageHelper storageHelper) {
        if (instance == null)
        {
            instance = new Wardrobe(storageHelper);
        }
    }

    public static Wardrobe getInstance() {
        return instance;
    }

    @Override
    public List<IWardrobeItem> getShirts() {
        return shirtList;
    }

    @Override
    public List<IWardrobeItem> getTrousers() {
        return trousersList;
    }

    @Override
    public void clear() {
        shirtList.clear();
        trousersList.clear();
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
    public void setStorageHelper(IStorageHelper storageHelper) {
        storageAdapter.setStorageHelper(storageHelper);
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
            case SHIRT: shirtList.add(wardrobeItem); break;
            case TROUSERS: trousersList.add(wardrobeItem); break;
        }
    }

    @Override
    public boolean removeWardrobeItem(String wardrobeItemString) {
        for (IWardrobeItem item : shirtList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                shirtList.remove(item);
                return true;
            }
        }

        for (IWardrobeItem item : trousersList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                trousersList.remove(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeWardrobeItem(Drawable drawable) {
        for (IWardrobeItem item : shirtList)
        {
            if (item.getDrawable() == drawable)
            {
                shirtList.remove(item);
                return true;
            }
        }

        for (IWardrobeItem item : trousersList)
        {
            if (item.getDrawable() == drawable)
            {
                trousersList.remove(item);
                return true;
            }
        }
        return false;
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

    private WardrobeItemType getItemTypeFromString(String itemTypeString) {
        if (itemTypeString.compareTo(SHIRT_STRING) == 0)
        {
            return WardrobeItemType.SHIRT;
        }
        else if (itemTypeString.compareTo(TROUSERS_STRING) == 0)
        {
            return WardrobeItemType.TROUSERS;
        }
        return WardrobeItemType.DEFAULT;
    }

    //TODO: This crap is used right now for injecting mock into unit test. Should be replaced by better solution
    public void setStorageAdapter(IStorageAdapter storageAdapter) {this.storageAdapter = storageAdapter;}
}
