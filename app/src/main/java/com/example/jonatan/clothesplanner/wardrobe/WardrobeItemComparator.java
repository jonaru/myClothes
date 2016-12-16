package com.example.jonatan.clothesplanner.wardrobe;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Created by Jonatan on 2016-12-14.
 */

public class WardrobeItemComparator implements Comparator<IWardrobeItem> {
    @Override
    public int compare(IWardrobeItem a, IWardrobeItem b) {
        return (a.getWardrobeItemString().compareTo(b.getWardrobeItemString()));
    }

    @Override
    public Comparator<IWardrobeItem> reversed() {
        return null;
    }

    @Override
    public Comparator<IWardrobeItem> thenComparing(Comparator<? super IWardrobeItem> other) {
        return null;
    }

    @Override
    public <U> Comparator<IWardrobeItem> thenComparing(Function<? super IWardrobeItem, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return null;
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<IWardrobeItem> thenComparing(Function<? super IWardrobeItem, ? extends U> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<IWardrobeItem> thenComparingInt(ToIntFunction<? super IWardrobeItem> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<IWardrobeItem> thenComparingLong(ToLongFunction<? super IWardrobeItem> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<IWardrobeItem> thenComparingDouble(ToDoubleFunction<? super IWardrobeItem> keyExtractor) {
        return null;
    }
}
