package com.example.jonatan.clothesplanner;

import android.view.View;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ClothesPlannerUnitTest {

    private static final String BLUE_SHIRT = "blue shirt";
    private static final String WHITE_SHIRT = "white shirt";

    @Mock
    View mMockView;

    @Test
    public void testGetWardrobeItemString() throws Exception {
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        assertEquals(BLUE_SHIRT, shirt.getWardrobeItemString());
    }

    @Test
    public void testGetWardrobeItemType() throws Exception {
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        assertEquals(WardrobeItemType.SHIRT, shirt.getWardrobeItemType());
    }

    @Test
    public void testFindWardrobeItemFromWardrobe() throws Exception {
        IWardrobe wardrobe = new Wardrobe();
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        wardrobe.addWardrobeItem(shirt);

        assertEquals(shirt, wardrobe.findWardrobeItem(BLUE_SHIRT));
    }

    @Test
    public void testAddWardrobeItemByString() throws Exception {
        IWardrobe wardrobe = new Wardrobe();
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        wardrobe.addWardrobeItem(BLUE_SHIRT);

        assertEquals(BLUE_SHIRT, wardrobe.findWardrobeItem(BLUE_SHIRT).getWardrobeItemString());
    }

    @Test
    public void testGetShirts() throws Exception {
        IWardrobe wardrobe = new Wardrobe();
        IWardrobeItem blueShirt = new Shirt(BLUE_SHIRT);
        IWardrobeItem whiteShirt = new Shirt(WHITE_SHIRT);
        wardrobe.addWardrobeItem(blueShirt);
        wardrobe.addWardrobeItem(whiteShirt);

        List<Shirt> shirts = wardrobe.getShirts();
        assertTrue(shirts.contains(blueShirt));
    }

        /*
        when(mMockView.getString(R.string.hello_word))
                .thenReturn(FAKE_STRING);
        */
}