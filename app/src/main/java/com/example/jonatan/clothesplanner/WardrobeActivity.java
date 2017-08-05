package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.util.List;

public class WardrobeActivity extends AppCompatActivity {

    private IWardrobe wardrobe;
    ViewPager upperItemsViewPager;
    ViewPager lowerItemsViewPager;
    ViewPager footwearViewPager;

    WardrobePagerAdapter upperItemAdapter;
    WardrobePagerAdapter lowerItemsAdapter;
    WardrobePagerAdapter footwearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
        wardrobe = Wardrobe.getInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        upperItemsViewPager = (ViewPager) findViewById(R.id.upper_body_pager);
        if (upperItemsViewPager != null) {
            upperItemAdapter = new WardrobePagerAdapter();
            upperItemsViewPager.setAdapter(upperItemAdapter);
        }

        lowerItemsViewPager = (ViewPager) findViewById(R.id.lower_body_pager);
        if (lowerItemsViewPager != null) {
            lowerItemsAdapter = new WardrobePagerAdapter();
            lowerItemsViewPager.setAdapter(lowerItemsAdapter);
        }

        footwearViewPager = (ViewPager) findViewById(R.id.footwear_pager);
        if (footwearViewPager != null) {
            footwearAdapter = new WardrobePagerAdapter();
            footwearViewPager.setAdapter(footwearAdapter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wardrobe.getInstance().storeWardrobe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addItemsFromWardrobe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_wardrobe:
                // Do nothing
                return true;

            case R.id.action_weekly_plan:
                Intent intent = new Intent(this, WeeklyPlanActivity.class);
                startActivity(intent);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        Intent intent = new Intent(this, PopUpWardrobeActivity.class);
        startActivity(intent);
    }

    private void addItemsFromWardrobe() {
        upperItemsViewPager.removeAllViews();
        lowerItemsViewPager.removeAllViews();
        List<IWardrobeItem> upperItemsList = wardrobe.getUpperItems();
        List<IWardrobeItem> lowerItemsList = wardrobe.getLowerItems();
        List<IWardrobeItem> footwearList = wardrobe.getFootwearItems();

        for (IWardrobeItem upperItem : upperItemsList)
        {
            addWardrobeItemToPager(upperItem);
        }

        for (IWardrobeItem lowerItem : lowerItemsList)
        {
            addWardrobeItemToPager(lowerItem);
        }

        for (IWardrobeItem footwear : footwearList)
        {
            addWardrobeItemToPager(footwear);
        }
    }

    private void addWardrobeItemToPager(IWardrobeItem addedWardrobeItem) {
        LinearLayout wardrobeItemViewGroup = new LinearLayout(this);
        addedWardrobeItem.addToView(wardrobeItemViewGroup);
        Button removeButton = (Button) createNewRemoveButton();
        wardrobeItemViewGroup.addView(removeButton);

        if (addedWardrobeItem.getWardrobeItemType() == WardrobeItemType.UPPER)
        {
            removeButton.setOnClickListener(new RemoveButtonOnClickListener(upperItemAdapter, upperItemsViewPager));
            int pageIndex = upperItemAdapter.addView(wardrobeItemViewGroup);
            upperItemsViewPager.setCurrentItem(pageIndex, true);
        }
        else if (addedWardrobeItem.getWardrobeItemType() == WardrobeItemType.LOWER)
        {
            removeButton.setOnClickListener(new RemoveButtonOnClickListener(lowerItemsAdapter, lowerItemsViewPager));
            int pageIndex = lowerItemsAdapter.addView(wardrobeItemViewGroup);
            lowerItemsViewPager.setCurrentItem(pageIndex, true);
        }
        else if (addedWardrobeItem.getWardrobeItemType() == WardrobeItemType.FOOTWEAR)
        {
            removeButton.setOnClickListener(new RemoveButtonOnClickListener(footwearAdapter, footwearViewPager));
            int pageIndex = footwearAdapter.addView(wardrobeItemViewGroup);
            footwearViewPager.setCurrentItem(pageIndex, true);
        }
    }

    private View createNewRemoveButton() {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final Button removeButton = new Button(this);
        removeButton.setLayoutParams(layoutParams);
        removeButton.setText(R.string.remove);
        removeButton.setGravity(View.FOCUS_RIGHT);
        return removeButton;
    }
}
