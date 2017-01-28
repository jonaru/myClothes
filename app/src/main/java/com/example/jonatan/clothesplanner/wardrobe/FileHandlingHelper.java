package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;

import com.example.jonatan.clothesplanner.R;
import com.example.jonatan.clothesplanner.WardrobeException;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2017-01-28.
 */

public class FileHandlingHelper implements IFileHandlingHelper {

    private final Context myContext;

    public FileHandlingHelper(Context context)
    {
        myContext = context;
    }

    @Override
    public void loadWardrobe(IWardrobe wardrobe) {
        try {
            readWardrobeFromFile(WardrobeItemType.SHIRT, wardrobe);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WardrobeException e) {
            e.printStackTrace();
        }

        try {
            readWardrobeFromFile(WardrobeItemType.TROUSERS, wardrobe);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WardrobeException e) {
            e.printStackTrace();
        }
    }

    private void readWardrobeFromFile(WardrobeItemType itemType, IWardrobe wardrobe) throws IOException, WardrobeException {
        FileInputStream fileInputStream = openWardrobeFileInputStream(itemType);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

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

    @Override
    public void storeWardrobe(IWardrobe wardrobe) {
        try {
            storeToFile(wardrobe.getShirts(), myContext.getResources().getString(R.string.saved_shirts));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            storeToFile(wardrobe.getTrousers(), myContext.getResources().getString(R.string.saved_trousers));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void storeToFile(List<IWardrobeItem> wardrobeItemList, String fileString) throws IOException {
        myContext.deleteFile(fileString);
        FileOutputStream fileOutputStream = myContext.openFileOutput(fileString, Context.MODE_PRIVATE);

        for (IWardrobeItem item : wardrobeItemList)
        {
            fileOutputStream.write(item.getWardrobeItemString().getBytes());
            fileOutputStream.write(System.getProperty("line.separator").getBytes());
        }

        fileOutputStream.close();
    }

    public void writeToWardrobeFile(String itemText, String itemTypeString) throws IOException {
        FileOutputStream fileOutputStream = null;

        if (itemTypeString.compareTo(myContext.getResources().getString(R.string.shirt)) == 0)
        {
            fileOutputStream = myContext.openFileOutput(myContext.getResources().getString(R.string.saved_shirts), Context.MODE_PRIVATE);
        }
        else if (itemTypeString.compareTo(myContext.getResources().getString(R.string.trousers)) == 0)
        {
            fileOutputStream = myContext.openFileOutput(myContext.getResources().getString(R.string.saved_trousers), Context.MODE_PRIVATE);
        }

        fileOutputStream.write(itemText.getBytes());
        fileOutputStream.close();
    }
}
