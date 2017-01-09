package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class WardrobeItem implements IWardrobeItem {
    private String wardrobeItemString;

    public WardrobeItem(String wardrobeItemInputString) {
        //noinspection RedundantStringConstructorCall
        wardrobeItemString = new String(wardrobeItemInputString);
    }

    public WardrobeItem() {
    }

    @Override
    public String getWardrobeItemString() {
        return wardrobeItemString;
    }

    @Override
    public WardrobeItemType getWardrobeItemType() {
        return WardrobeItemType.DEFAULT;
    }

    @Override
    public View getView(Context context) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setText(this.getWardrobeItemString());
        return textView;
    }
}
