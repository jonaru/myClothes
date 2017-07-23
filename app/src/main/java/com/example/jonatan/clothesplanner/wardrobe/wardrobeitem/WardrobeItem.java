package com.example.jonatan.clothesplanner.wardrobe.wardrobeitem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class WardrobeItem implements IWardrobeItem {

    private String itemDescription;
    protected Drawable drawable;
    private WardrobeItemType itemType;

    public WardrobeItem() {
        itemType = WardrobeItemType.DEFAULT;
        itemDescription = null;
        drawable = null;
    }

    public WardrobeItem(String description, WardrobeItemType type) {
        //noinspection RedundantStringConstructorCall
        itemDescription = new String(description);
        drawable = null;
        itemType = type;
    }

    public WardrobeItem(String description, Drawable selectedDrawable, WardrobeItemType type) {
        itemDescription = new String(description);
        drawable = selectedDrawable;
        itemType = type;
    }

    public String getWardrobeItemString() {
        return itemDescription;
    }

    public WardrobeItemType getWardrobeItemType() {
        return itemType;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public View addToView(LinearLayout layout) {
        Context context = layout.getContext();
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (drawable != null)
        {
            final ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(drawable);
            imageView.setLayoutParams(layoutParams);
            layout.addView(imageView);
        }

        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setText(this.getWardrobeItemString());
        layout.addView(textView);

        return layout;
    }
}
