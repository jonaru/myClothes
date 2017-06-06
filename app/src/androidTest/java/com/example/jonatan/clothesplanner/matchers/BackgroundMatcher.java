package com.example.jonatan.clothesplanner.matchers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Jonatan on 2017-06-06.
 */

public class BackgroundMatcher extends TypeSafeMatcher<View> {
    private final int resourceId;
    String resourceName;

    public BackgroundMatcher(int resourceId) {
        super(View.class);
        this.resourceId = resourceId;
    }

    @Override
    public boolean matchesSafely(View view) {
        return sameBitmap(view.getContext(), view.getBackground(), resourceId);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has background resource " + resourceId);
    }

    private static boolean sameBitmap(Context context, Drawable drawable, int resourceId) {
        Drawable otherDrawable = ContextCompat.getDrawable(context, resourceId);
        if (drawable == null || otherDrawable == null) {
            return false;
        }
        if (drawable instanceof StateListDrawable && otherDrawable instanceof StateListDrawable) {
            drawable = drawable.getCurrent();
            otherDrawable = otherDrawable.getCurrent();
        }

        if (drawable instanceof GradientDrawable) {
            Bitmap bitmap = getBitmapFromGradientDrawable((GradientDrawable)drawable);
            Bitmap otherBitmap = getBitmapFromGradientDrawable((GradientDrawable)otherDrawable);
            return bitmap.sameAs(otherBitmap);
        }

        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap otherBitmap = ((BitmapDrawable) otherDrawable).getBitmap();
            return bitmap.sameAs(otherBitmap);
        }
        return false;
    }

    private static Bitmap getBitmapFromGradientDrawable(GradientDrawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, 10, 10);
        drawable.draw(canvas);

        return bitmap;
    }
}
