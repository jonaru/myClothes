package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Initialize Wardrobe
        Wardrobe.initInstance();

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

    public void goToWardrobe(View view) {
        Intent intent = new Intent(this, WardrobeActivity.class);
        startActivity(intent);
    }

    public void goToWeeklyPlan(View view) {
        Intent intent = new Intent(this, WeeklyPlanActivity.class);
        startActivity(intent);
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
                wardrobe.addWardrobeItem(trimmedLine, getResources().getString(R.string.shirt));
            }
            else if (itemType  == WardrobeItemType.TROUSERS)
            {
                wardrobe.addWardrobeItem(trimmedLine, getResources().getString(R.string.trousers));
            }
        }

        reader.close();
    }

    private FileInputStream openWardrobeFileInputStream(WardrobeItemType itemType) throws WardrobeException {
        if (itemType  == WardrobeItemType.SHIRT)
        {
            try {
                return openFileInput(getResources().getString(R.string.saved_shirts));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new WardrobeException();
            }
        }
        else if (itemType  == WardrobeItemType.TROUSERS)
        {
            try {
                return openFileInput(getResources().getString(R.string.saved_trousers));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new WardrobeException();
            }
        }
        return null;
    }
}
