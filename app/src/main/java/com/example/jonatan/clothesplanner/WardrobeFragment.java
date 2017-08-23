package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WardrobeFragment extends Fragment implements View.OnClickListener {
    private IWardrobe wardrobe;
    ViewPager upperItemsViewPager;
    ViewPager lowerItemsViewPager;
    ViewPager footwearViewPager;

    WardrobePagerAdapter upperItemAdapter;
    WardrobePagerAdapter lowerItemsAdapter;
    WardrobePagerAdapter footwearAdapter;

    public WardrobeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wardrobe = Wardrobe.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wardrobe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        upperItemsViewPager = (ViewPager) getActivity().findViewById(R.id.upper_body_pager);
        if (upperItemsViewPager != null) {
            upperItemAdapter = new WardrobePagerAdapter();
            upperItemsViewPager.setAdapter(upperItemAdapter);
        }

        lowerItemsViewPager = (ViewPager) getActivity().findViewById(R.id.lower_body_pager);
        if (lowerItemsViewPager != null) {
            lowerItemsAdapter = new WardrobePagerAdapter();
            lowerItemsViewPager.setAdapter(lowerItemsAdapter);
        }

        footwearViewPager = (ViewPager) getActivity().findViewById(R.id.footwear_pager);
        if (footwearViewPager != null) {
            footwearAdapter = new WardrobePagerAdapter();
            footwearViewPager.setAdapter(footwearAdapter);
        }

        Button addItemButton = (Button) getActivity().findViewById(R.id.button);
        addItemButton.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Wardrobe.getInstance().storeWardrobe();
    }

    @Override
    public void onResume() {
        super.onResume();
        addItemsFromWardrobe();
    }

    public void onClick(@SuppressWarnings("UnusedParameters") View view) {
        Intent intent = new Intent(getActivity(), PopUpWardrobeActivity.class);
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
        LinearLayout wardrobeItemViewGroup = new LinearLayout(getActivity());
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
        final Button removeButton = new Button(getActivity());
        removeButton.setLayoutParams(layoutParams);
        removeButton.setText(R.string.remove);
        removeButton.setGravity(View.FOCUS_RIGHT);
        return removeButton;
    }
}
