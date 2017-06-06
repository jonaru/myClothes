package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

public class PopUpWardrobeActivity extends Activity {
    private Drawable selectedDrawable;
    private WardrobeItemType selectedItemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_wardrobe);
        selectedDrawable = null;
        selectedItemType = null;
    }

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        IWardrobe wardrobe = Wardrobe.getInstance();

        EditText editText = (EditText) findViewById(R.id.editText_add_item);
        Spinner wardrobeSpinner = (Spinner) findViewById(R.id.wardrobe_spinner);
        String itemTypeString = (String) wardrobeSpinner.getSelectedItem();
        String itemText = editText.getText().toString();


        if (selectedDrawable != null)
        {
            wardrobe.addWardrobeItem(itemText, selectedItemType, selectedDrawable);
        }
        else
        {
            wardrobe.addWardrobeItem(itemText, itemTypeString);
        }

        finish();
    }

    public void selectShirt(@SuppressWarnings("UnusedParameters") View view) {
        Drawable highlight = ResourcesCompat.getDrawable(getResources(), R.drawable.highlight, null);
        view.setBackground(highlight);
        ImageButton imageButton = (ImageButton)view;
        selectedDrawable = imageButton.getDrawable();
        selectedItemType = WardrobeItemType.SHIRT;
    }

    public void selectTrousers(@SuppressWarnings("UnusedParameters") View view) {
        Drawable highlight = ResourcesCompat.getDrawable(getResources(), R.drawable.highlight, null);
        view.setBackground(highlight);
        ImageButton imageButton = (ImageButton)view;
        selectedDrawable = imageButton.getDrawable();
        selectedItemType = WardrobeItemType.TROUSERS;
    }
}
