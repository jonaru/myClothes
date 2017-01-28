package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;

import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Wardrobe implements IWardrobe {
    private static final String SHIRT_STRING = "Shirt";
    private static final String TROUSERS_STRING = "Trousers";
    private final List<IWardrobeItem> wardrobeItemList = new ArrayList<>();
    private final ArrayList<IWardrobeItem> shirtList = new ArrayList<>();
    private final ArrayList<IWardrobeItem> trousersList = new ArrayList<>();
    private static Wardrobe instance;
    private IFileHandlingHelper fileHandlingHelper;

    private Wardrobe(Context context)
    {
        fileHandlingHelper = new FileHandlingHelper(context);
    }

    public static void initInstance(Context context) {
        if (instance == null)
        {
            instance = new Wardrobe(context);
        }
    }

    public static Wardrobe getInstance() {
        return instance;
    }


    @Override
    public IWardrobeItem addWardrobeItem(String itemString, String itemTypeString) {
        IWardrobeItem itemToAdd = null;
        if (itemTypeString.compareTo(SHIRT_STRING) == 0)
        {
            itemToAdd = new Shirt(itemString);
        }
        else if (itemTypeString.compareTo(TROUSERS_STRING) == 0)
        {
            itemToAdd = new Trousers(itemString);
        }
        addWardrobeItem(itemToAdd);

        /*
        try {
            fileHandlingHelper.writeToWardrobeFile(itemString, itemTypeString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        return itemToAdd;
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
    public void loadWardrobe() {
        clear();
        fileHandlingHelper.loadWardrobe(this);
    }

    @Override
    public void storeWardrobe() {
        fileHandlingHelper.storeWardrobe(this);
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
