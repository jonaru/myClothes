package com.example.jonatan.clothesplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class WardrobeActivity extends AppCompatActivity {

    ViewPager wardrobeViewPager;
    ActionTabsViewPagerAdapter actionTabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        //set up actionbar
        Toolbar actionBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(actionBar);

        //set up fragment pager
        wardrobeViewPager = (ViewPager) findViewById(R.id.wardrobe_viewpager);
        actionTabsPagerAdapter = new ActionTabsViewPagerAdapter(getSupportFragmentManager(), this);
        wardrobeViewPager.setAdapter(actionTabsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_wardrobe:
                wardrobeViewPager.setCurrentItem(0);
                return true;

            case R.id.action_weekly_plan:
                wardrobeViewPager.setCurrentItem(1);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
