package com.example.jonatan.clothesplanner;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ClothesPlannerUnitTest {

    private static final String BLUE_SHIRT = "blue shirt";

    @Test
    public void testAddWardrobeItem() throws Exception {
        IWardrobe wardrobe = new Wardrobe();
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);

        assertEquals(0, wardrobe.addWardrobeItem(shirt));
    }

    @Test
    public void testGetWardrobeItemString() throws Exception {
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);

        assertEquals(BLUE_SHIRT, shirt.getWardrobeItemString());
    }
}