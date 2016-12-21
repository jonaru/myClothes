package com.example.jonatan.clothesplanner;

import android.view.View;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ClothesPlannerUnitTest {

    private static final String BLUE_SHIRT = "blue shirt";

    @Mock
    View mMockView;

    @Test
    public void testGetWardrobeItemString() throws Exception {
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        assertEquals(BLUE_SHIRT, shirt.getWardrobeItemString());
    }

    @Test
    public void testFindWardrobeItemFromWardrobe() throws Exception {
        IWardrobe wardrobe = new Wardrobe();
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        wardrobe.addWardrobeItem(shirt);

        assertEquals(shirt, wardrobe.findWardrobeItem(BLUE_SHIRT));
    }

        /*
        when(mMockView.getString(R.string.hello_word))
                .thenReturn(FAKE_STRING);
        */
}