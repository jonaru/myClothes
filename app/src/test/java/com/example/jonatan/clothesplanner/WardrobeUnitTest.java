package com.example.jonatan.clothesplanner;

import android.content.Context;

import com.example.jonatan.clothesplanner.wardrobe.IFileHandlingHelper;
import com.example.jonatan.clothesplanner.wardrobe.IStorageHelper;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType.SHIRT;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

/**
 * Unit test of Wardrobe classes
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
    Context mContext;

    @Mock
    IWardrobe mWardrobe;

    @Mock
    IStorageHelper mFileHandlingHelper;

    @InjectMocks
    Wardrobe wardrobe;

    @Before
    public void initSingletons() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mFileHandlingHelper).loadWardrobe(mWardrobe);
        Wardrobe.initInstance(mFileHandlingHelper);
    }

    @After
    public void cleanUp() {
        IWardrobe wardrobe = Wardrobe.getInstance();
        wardrobe.clear();
    }

    @Test
    public void testGetWardrobeItemString() throws Exception {
        IWardrobeItem shirt = new WardrobeItem(BLUE_SHIRT, WardrobeItemType.SHIRT);
        assertEquals(BLUE_SHIRT, shirt.getWardrobeItemString());
    }

    @Test
    public void testGetWardrobeItemType() throws Exception {
        IWardrobeItem shirt = new WardrobeItem(BLUE_SHIRT, WardrobeItemType.SHIRT);
        assertEquals(SHIRT, shirt.getWardrobeItemType());
    }

    @Test
    public void testFindWardrobeItemFromWardrobe() throws Exception {
        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem shirt = new WardrobeItem(BLUE_SHIRT, WardrobeItemType.SHIRT);
        wardrobe.addWardrobeItem(shirt);

        assertEquals(shirt, wardrobe.findWardrobeItem(BLUE_SHIRT));
    }

    @Test
    public void testAddWardrobeItemByString() throws Exception {
        IWardrobeItem shirt = new WardrobeItem(BLUE_SHIRT, WardrobeItemType.SHIRT);
        //doNothing().when(mFileHandlingHelper).writeToWardrobeFile(BLUE_SHIRT, SHIRT_STRING);
        wardrobe.addWardrobeItem(BLUE_SHIRT, SHIRT_STRING);

        assertEquals(BLUE_SHIRT, wardrobe.findWardrobeItem(BLUE_SHIRT).getWardrobeItemString());
    }

    @Test
    public void testGetShirts() throws Exception {
        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem blueShirt = new WardrobeItem(BLUE_SHIRT, WardrobeItemType.SHIRT);
        IWardrobeItem whiteShirt = new WardrobeItem(WHITE_SHIRT, WardrobeItemType.SHIRT);
        wardrobe.addWardrobeItem(blueShirt);
        wardrobe.addWardrobeItem(whiteShirt);

        List<IWardrobeItem> shirts = wardrobe.getShirts();
        assertTrue(shirts.contains(blueShirt));
    }

    @Test
    public void testGetTrousers() throws Exception {
        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem khakis = new WardrobeItem(KHAKIS, WardrobeItemType.TROUSERS);
        IWardrobeItem jeans = new WardrobeItem(JEANS, WardrobeItemType.TROUSERS);
        wardrobe.addWardrobeItem(khakis);
        wardrobe.addWardrobeItem(jeans);

        List<IWardrobeItem> trousers = wardrobe.getTrousers();
        assertTrue(trousers.contains(khakis));
        assertTrue(trousers.contains(jeans));
    }

    @Test
    public void testGetWardrobeInstance() throws Exception {
        Wardrobe wardrobe = Wardrobe.getInstance();
        assertNotNull(wardrobe);
    }
}