package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

public class PopUpWardrobeActivity extends Activity {

    private static final double POPUP_SCREEN_PERCENTAGE = 0.8;
    private Drawable selectedDrawable;
    private WardrobeItemType selectedItemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        setTheme(R.style.AppTheme_PopUpWardrobeTheme);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1.0f;    // lower than one makes it more transparent
        params.dimAmount = 0f;  // set it higher if you want to dim behind the window
        getWindow().setAttributes(params);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * POPUP_SCREEN_PERCENTAGE), (int)(height * POPUP_SCREEN_PERCENTAGE));
        */
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
            wardrobe.addTempWardrobeItem(itemText, selectedItemType, selectedDrawable);
        }
        else
        {
            wardrobe.addTempWardrobeItem(itemText, itemTypeString);
        }

        finish();
    }

    public void selectBlueShirt(@SuppressWarnings("UnusedParameters") View view) {
        ImageButton imageButton = (ImageButton)view;
        selectedDrawable = imageButton.getDrawable();
        selectedItemType = WardrobeItemType.SHIRT;
    }
}
