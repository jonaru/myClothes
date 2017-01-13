package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.RemoveButtonOnClickListener;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WardrobeActivity extends AppCompatActivity {

    private IWardrobe wardrobe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

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
        addWardrobeItemToView(addedWardrobeItem);
        editText.setText("");

        try {
            writeToWardrobeFile(itemText, itemTypeString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            addWardrobeItemToView(shirt);
        }

        for (IWardrobeItem trousers : trousersList)
        {
            addWardrobeItemToView(trousers);
        }
    }

    private void addWardrobeItemToView(IWardrobeItem addedWardrobeItem) {
        LinearLayout wardrobeItemViewGroup = new LinearLayout(this);
        wardrobeItemViewGroup.addView(addedWardrobeItem.getView(this));
        wardrobeItemViewGroup.addView(createNewRemoveButton());

        if (addedWardrobeItem.getWardrobeItemType() == WardrobeItemType.SHIRT)
        {
            ViewPager shirtPager = (ViewPager) findViewById(R.id.shirt_layout);
            shirtPager.addView(wardrobeItemViewGroup);
        }
        else if (addedWardrobeItem.getWardrobeItemType() == WardrobeItemType.TROUSERS)
        {
            ViewPager trousersPager = (ViewPager) findViewById(R.id.trousers_layout);
            trousersPager.addView(wardrobeItemViewGroup);
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
        removeButton.setOnClickListener(new RemoveButtonOnClickListener(this));
        return removeButton;
    }
}
