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
        String itemString = textView.getText().toString();
        myPagerAdapter.removeView(myViewPager, parentView);

        //Remove from Wardrobe
        IWardrobe wardrobe = Wardrobe.getInstance();
        wardrobe.removeWardrobeItem(itemString);
    }
}
