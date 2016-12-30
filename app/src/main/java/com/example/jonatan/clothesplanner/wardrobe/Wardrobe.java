package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.MainActivity;
import com.example.jonatan.clothesplanner.R;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Wardrobe implements IWardrobe {
    private final List<IWardrobeItem> wardrobeItemList = new ArrayList<>();

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
        wardrobeItemList.add(0, wardrobeItem);
    }

    @Override
    public IWardrobeItem findWardrobeItem(String wardrobeItemString) {
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
