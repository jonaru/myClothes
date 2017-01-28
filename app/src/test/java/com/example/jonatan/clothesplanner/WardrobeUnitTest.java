package com.example.jonatan.clothesplanner;

import android.view.View;

import com.example.jonatan.clothesplanner.wardrobe.FileHandlingHelper;
import com.example.jonatan.clothesplanner.wardrobe.IFileHandlingHelper;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType.SHIRT;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class WardrobeUnitTest {

    private static final String BLUE_SHIRT = "blue shirt";
    private static final String WHITE_SHIRT = "white shirt";
    private static final String KHAKIS = "khakis";
    private static final String JEANS = "jeans";
    private static final String SHIRT_STRING = "Shirt";

    @Mock
    static IFileHandlingHelper mFileHandlingHelper;

    @Before
    public void initSingletons() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mFileHandlingHelper).loadWardrobe();
        Wardrobe.initInstance();
    }

    @After
    public void cleanUp() {
        IWardrobe wardrobe = Wardrobe.getInstance();
        wardrobe.clear();
    }

    @Test
    public void testGetWardrobeItemString() throws Exception {
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        assertEquals(BLUE_SHIRT, shirt.getWardrobeItemString());
    }

    @Test
    public void testGetWardrobeItemType() throws Exception {
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        assertEquals(SHIRT, shirt.getWardrobeItemType());
    }

    @Test
    public void testFindWardrobeItemFromWardrobe() throws Exception {
        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        wardrobe.addWardrobeItem(shirt);

        assertEquals(shirt, wardrobe.findWardrobeItem(BLUE_SHIRT));
    }

    @Test
    public void testAddWardrobeItemByString() throws Exception {
        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        wardrobe.addWardrobeItem(BLUE_SHIRT, SHIRT_STRING);

        assertEquals(BLUE_SHIRT, wardrobe.findWardrobeItem(BLUE_SHIRT).getWardrobeItemString());
    }

    @Test
    public void testGetShirts() throws Exception {
        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem blueShirt = new Shirt(BLUE_SHIRT);
        IWardrobeItem whiteShirt = new Shirt(WHITE_SHIRT);
        wardrobe.addWardrobeItem(blueShirt);
        wardrobe.addWardrobeItem(whiteShirt);

        List<Shirt> shirts = wardrobe.getShirts();
        assertTrue(shirts.contains(blueShirt));
    }

    @Test
    public void testGetTrousers() throws Exception {
        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem khakis = new Trousers(KHAKIS);
        IWardrobeItem jeans = new Trousers(JEANS);
        wardrobe.addWardrobeItem(khakis);
        wardrobe.addWardrobeItem(jeans);

        List<Trousers> trousers = wardrobe.getTrousers();
        assertTrue(trousers.contains(khakis));
        assertTrue(trousers.contains(jeans));
    }

    @Test
    public void testGetWardrobeInstance() throws Exception {
        Wardrobe wardrobe = Wardrobe.getInstance();
        assertNotNull(wardrobe);
    }
}