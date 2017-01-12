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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readWardrobeFromFile(WardrobeItemType.TROUSERS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    private void readWardrobeFromFile(WardrobeItemType itemType) throws IOException {
        FileInputStream fileInputStream = null;

        if (itemType  == WardrobeItemType.SHIRT)
        {
            try {
                fileInputStream = openFileInput(getResources().getString(R.string.saved_shirts));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new IOException();
            }
        }
        else if (itemType  == WardrobeItemType.TROUSERS)
        {
            try {
                fileInputStream = openFileInput(getResources().getString(R.string.saved_trousers));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new IOException();
            }
        }

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
}
