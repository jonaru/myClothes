package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.RemoveButtonOnClickListener;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

        wardrobe = Wardrobe.getInstance();
        addItemsFromWardrobe();
    }

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        if (wardrobe == null) {
            throw new WardrobeException();
        }

        EditText editText = (EditText) findViewById(R.id.editText_add_item);
        Spinner wardrobeSpinner = (Spinner) findViewById(R.id.wardrobe_spinner);
        String itemTypeString = (String) wardrobeSpinner.getSelectedItem();
        String itemText = editText.getText().toString();

        IWardrobeItem addedWardrobeItem = wardrobe.addWardrobeItem(itemText, itemTypeString);
        addWardrobeItemToPager(addedWardrobeItem);
        editText.setText("");

        try {
            writeToWardrobeFile(itemText, itemTypeString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This should be handled by Wardrobe through a FileHandlerHelper class
    private void writeToWardrobeFile(String itemText, String itemTypeString) throws IOException {
        FileOutputStream fileOutputStream = null;

        if (itemTypeString.compareTo(getResources().getString(R.string.shirt)) == 0)
        {
            fileOutputStream = openFileOutput(getResources().getString(R.string.saved_shirts), Context.MODE_PRIVATE);
        }
        else if (itemTypeString.compareTo(getResources().getString(R.string.trousers)) == 0)
        {
            fileOutputStream = openFileOutput(getResources().getString(R.string.saved_trousers), Context.MODE_PRIVATE);
        }

        fileOutputStream.write(itemText.getBytes());
        fileOutputStream.close();
    }

    private void addItemsFromWardrobe() {
        List<Shirt> shirtList = wardrobe.getShirts();
        List<Trousers> trousersList = wardrobe.getTrousers();

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
            removeButton.setOnClickListener(new RemoveButtonOnClickListener(this, shirtAdapter, shirtViewPager));
            int pageIndex = shirtAdapter.addView(wardrobeItemViewGroup);
            shirtViewPager.setCurrentItem(pageIndex, true);
        }
        else if (addedWardrobeItem.getWardrobeItemType() == WardrobeItemType.TROUSERS)
        {
            removeButton.setOnClickListener(new RemoveButtonOnClickListener(this, trousersAdapter, trousersViewPager));
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
