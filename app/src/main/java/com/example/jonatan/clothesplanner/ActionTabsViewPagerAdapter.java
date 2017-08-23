package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jonatan on 2017-08-23.
 */

public class ActionTabsViewPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_ITEMS = 2;
    Context myContext;

    public ActionTabsViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        myContext = context;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return Fragment.instantiate(myContext, WardrobeFragment.class.getName());
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return Fragment.instantiate(myContext, WeeklyPlanFragment.class.getName());
            default:
                return null;
        }
    }
}
