package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.R;
import com.example.jonatan.clothesplanner.WardrobePagerAdapter;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Jonatan on 2016-12-18.
 */

public class RemoveButtonOnClickListener implements View.OnClickListener {
    private final Activity myActivity;
    private final WardrobePagerAdapter myPagerAdapter;
    private final ViewPager myViewPager;

    public RemoveButtonOnClickListener(Activity inputMainActivity, WardrobePagerAdapter pagerAdapter, ViewPager viewPager){
        super();

        myActivity = inputMainActivity;
        myPagerAdapter = pagerAdapter;
        myViewPager = viewPager;
    }

    @Override
    public void onClick(View view) {
        //Remove from view
        ViewGroup parentView = (ViewGroup) view.getParent();
        TextView textView = (TextView) parentView.getChildAt(0);
        ViewGroup grandparentView = (ViewGroup) parentView.getParent();
        myPagerAdapter.removeView(myViewPager, parentView);

        //Remove from file
        String wardrobeItemFile = getWardrobeItemFile(grandparentView);
        String itemString = textView.getText().toString();
        try {
            removeWardrobeItemFromFile(itemString, wardrobeItemFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Remove from Wardrobe
        IWardrobe wardrobe = Wardrobe.getInstance();
        wardrobe.removeWardrobeItem(itemString);
    }

    private String getWardrobeItemFile(ViewGroup grandparentView) {
        if (grandparentView.getId() == R.id.shirt_pager)
        {
            return grandparentView.getResources().getString(R.string.saved_shirts);
        }
        else if (grandparentView.getId() == R.id.trousers_pager)
        {
            return grandparentView.getResources().getString(R.string.saved_trousers);
        }
        return null;
    }

    //This should be handled by Wardrobe through a FileHandlerHelper class
    private boolean removeWardrobeItemFromFile(String lineToRemove, String wardrobeItemFile) throws IOException {
        FileInputStream fileInputStream = myActivity.openFileInput(wardrobeItemFile);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        final String myTempFileString = "myTempFile.txt";
        FileOutputStream fileOutputStream = myActivity.openFileOutput(myTempFileString, Context.MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        writer.close();
        reader.close();

        File inputFile = new File(myActivity.getFilesDir()+"/"+wardrobeItemFile);
        File tempFile = new File(myActivity.getFilesDir()+"/"+myTempFileString);
        return tempFile.renameTo(inputFile);
    }
}
