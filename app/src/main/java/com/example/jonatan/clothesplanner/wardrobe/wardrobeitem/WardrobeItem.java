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
public abstract class WardrobeItem implements IWardrobeItem {

    protected String itemDescription = null;
    protected Drawable drawable = null;

    public WardrobeItem() {
    }

    public WardrobeItem(String description) {
        //noinspection RedundantStringConstructorCall
        itemDescription = new String(description);
    }

    public WardrobeItem(String description, Drawable selectedDrawable) {
        itemDescription = new String(description);
        drawable = selectedDrawable;
    }

    public String getWardrobeItemString() {
        return itemDescription;
    }

    public Drawable getDrawable() {
        return drawable;
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
        if (drawable == null)
        {
            final TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setText(this.getWardrobeItemString());
            return textView;
        }
        else
        {
            final ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(drawable);
            imageView.setLayoutParams(layoutParams);
            return imageView;
        }
    }
}
