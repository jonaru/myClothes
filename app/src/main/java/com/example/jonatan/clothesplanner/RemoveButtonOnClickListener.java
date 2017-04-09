package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private final WardrobePagerAdapter myPagerAdapter;
    private final ViewPager myViewPager;

    public RemoveButtonOnClickListener(WardrobePagerAdapter pagerAdapter, ViewPager viewPager){
        super();

        myPagerAdapter = pagerAdapter;
        myViewPager = viewPager;
    }

    @Override
    public void onClick(View view) {
        IWardrobe wardrobe = Wardrobe.getInstance();
        ViewGroup parentView = (ViewGroup) view.getParent();

        View itemView = parentView.getChildAt(0);
        if (itemView instanceof TextView)
        {
            String itemString = ((TextView)itemView).getText().toString();
            //Remove from Wardrobe
            wardrobe.removeWardrobeItem(itemString);
        }
        else if (itemView instanceof ImageView)
        {
            Drawable drawable = ((ImageView)itemView).getDrawable();
            //Remove from Wardrobe
            wardrobe.removeWardrobeItem(drawable);
        }

        //Remove from view
        myPagerAdapter.removeView(myViewPager, parentView);
    }
}
