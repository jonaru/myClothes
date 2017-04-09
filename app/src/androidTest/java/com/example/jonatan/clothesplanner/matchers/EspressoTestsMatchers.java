package com.example.jonatan.clothesplanner.matchers;

import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by Jonatan on 2017-04-09.
 */

public class EspressoTestsMatchers {
    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }
}
