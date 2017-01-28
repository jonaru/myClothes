package com.example.jonatan.clothesplanner.wardrobe;

import android.app.Activity;
import android.content.Context;

import com.example.jonatan.clothesplanner.R;
import com.example.jonatan.clothesplanner.WardrobeException;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Jonatan on 2017-01-28.
 */

public class FileHandlingHelper implements IFileHandlingHelper {

    private final Context myContext;

    public FileHandlingHelper(Context context)
    {
        myContext = context;
        Wardrobe.initInstance();
    }

    @Override
    public void loadWardrobe() {
        try {
            readWardrobeFromFile(WardrobeItemType.SHIRT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WardrobeException e) {
            e.printStackTrace();
        }

        try {
            readWardrobeFromFile(WardrobeItemType.TROUSERS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WardrobeException e) {
            e.printStackTrace();
        }
    }

    private void readWardrobeFromFile(WardrobeItemType itemType) throws IOException, WardrobeException {
        FileInputStream fileInputStream = openWardrobeFileInputStream(itemType);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        IWardrobe wardrobe = Wardrobe.getInstance();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (itemType  == WardrobeItemType.SHIRT)
            {
                wardrobe.addWardrobeItem(trimmedLine, myContext.getResources().getString(R.string.shirt));
            }
            else if (itemType  == WardrobeItemType.TROUSERS)
            {
                wardrobe.addWardrobeItem(trimmedLine, myContext.getResources().getString(R.string.trousers));
            }
        }

        reader.close();
    }

    private FileInputStream openWardrobeFileInputStream(WardrobeItemType itemType) throws WardrobeException {
        if (itemType  == WardrobeItemType.SHIRT)
        {
            try {
                return myContext.openFileInput(myContext.getResources().getString(R.string.saved_shirts));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new WardrobeException();
            }
        }
        else if (itemType  == WardrobeItemType.TROUSERS)
        {
            try {
                return myContext.openFileInput(myContext.getResources().getString(R.string.saved_trousers));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new WardrobeException();
            }
        }
        return null;
    }
}
