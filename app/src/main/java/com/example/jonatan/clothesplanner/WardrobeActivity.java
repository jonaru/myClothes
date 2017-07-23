package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.util.List;

public class WardrobeActivity extends Activity {

    private IWardrobe wardrobe;
    ViewPager upperItemsViewPager;
    ViewPager lowerItemsViewPager;

    WardrobePagerAdapter upperItemAdapter;
    WardrobePagerAdapter lowerItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
        wardrobe = Wardrobe.getInstance();

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

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        Intent intent = new Intent(this, PopUpWardrobeActivity.class);
        startActivity(intent);
    }

    private void addItemsFromWardrobe() {
        upperItemsViewPager.removeAllViews();
        lowerItemsViewPager.removeAllViews();
        List<IWardrobeItem> upperItemsList = wardrobe.getUpperItems();
        List<IWardrobeItem> lowerItemsList = wardrobe.getLowerItems();

        for (IWardrobeItem upperItem : upperItemsList)
        {
            addWardrobeItemToPager(upperItem);
        }

        for (IWardrobeItem lowerItem : lowerItemsList)
        {
            addWardrobeItemToPager(lowerItem);
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
