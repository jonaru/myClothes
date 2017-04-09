package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.content.Context;
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
    ViewPager shirtViewPager;
    ViewPager trousersViewPager;

    WardrobePagerAdapter shirtAdapter;
    WardrobePagerAdapter trousersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
        wardrobe = Wardrobe.getInstance();

        shirtViewPager = (ViewPager) findViewById(R.id.shirt_pager);
        if (shirtViewPager != null) {
            shirtAdapter = new WardrobePagerAdapter();
            shirtViewPager.setAdapter(shirtAdapter);
        }

        trousersViewPager = (ViewPager) findViewById(R.id.trousers_pager);
        if (trousersViewPager != null) {
            trousersAdapter = new WardrobePagerAdapter();
            trousersViewPager.setAdapter(trousersAdapter);
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
        shirtViewPager.removeAllViews();
        trousersViewPager.removeAllViews();
        List<IWardrobeItem> shirtList = wardrobe.getShirts();
        List<IWardrobeItem> trousersList = wardrobe.getTrousers();

        for (IWardrobeItem shirt : shirtList)
        {
            addWardrobeItemToPager(shirt);
        }

        for (IWardrobeItem trousers : trousersList)
        {
            addWardrobeItemToPager(trousers);
        }
    }

    private void addWardrobeItemToPager(IWardrobeItem addedWardrobeItem) {
        LinearLayout wardrobeItemViewGroup = new LinearLayout(this);
        wardrobeItemViewGroup.addView(addedWardrobeItem.getView(this));
        Button removeButton = (Button) createNewRemoveButton();
        wardrobeItemViewGroup.addView(removeButton);

        if (addedWardrobeItem.getWardrobeItemType() == WardrobeItemType.SHIRT)
        {
            removeButton.setOnClickListener(new RemoveButtonOnClickListener(shirtAdapter, shirtViewPager));
            int pageIndex = shirtAdapter.addView(wardrobeItemViewGroup);
            shirtViewPager.setCurrentItem(pageIndex, true);
        }
        else if (addedWardrobeItem.getWardrobeItemType() == WardrobeItemType.TROUSERS)
        {
            removeButton.setOnClickListener(new RemoveButtonOnClickListener(trousersAdapter, trousersViewPager));
            int pageIndex = trousersAdapter.addView(wardrobeItemViewGroup);
            trousersViewPager.setCurrentItem(pageIndex, true);
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
