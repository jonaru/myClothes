package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import com.example.jonatan.clothesplanner.matchers.DrawableMatcher;
import com.example.jonatan.clothesplanner.wardrobe.IStorageHelper;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobedb.WardrobeDbHelper;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Jonatan on 2017-04-23.
 */
/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static WardrobeDbHelper db;
    static private String KHAKIS = "khakis";
    static private String BLUE_SHIRT = "blue shirt";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.vikingtech.wardrober", appContext.getPackageName());
    }

    @Test
    public void storeLoadWardrobeTest() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new WardrobeDbHelper(InstrumentationRegistry.getTargetContext());
        Wardrobe.initInstance(((IStorageHelper)db));
        IWardrobe wardrobe = Wardrobe.getInstance();

        //Store some clothes in the wardrobe db
        Drawable blue_shirt_drawable = ContextCompat.getDrawable(appContext, R.drawable.shirt_blue);
        Drawable khakis_drawable = ContextCompat.getDrawable(appContext, R.drawable.khaki_trousers);
        IWardrobeItem shirt = new WardrobeItem(BLUE_SHIRT, blue_shirt_drawable, WardrobeItemType.SHIRT);
        IWardrobeItem trousers = new WardrobeItem(KHAKIS, khakis_drawable, WardrobeItemType.TROUSERS);
        wardrobe.addWardrobeItem(shirt);
        wardrobe.addWardrobeItem(trousers);
        db.storeWardrobe(wardrobe);

        //Delete the clothes from the Wardrobe and make sure they don't exists
        wardrobe.clear();
        assertNull(wardrobe.findWardrobeItem(BLUE_SHIRT));
        assertNull(wardrobe.findWardrobeItem(KHAKIS));

        //Load them again from the db
        db.loadWardrobe(wardrobe);
        assertNotNull(wardrobe.findWardrobeItem(BLUE_SHIRT));
        assertNotNull(wardrobe.findWardrobeItem(KHAKIS));

        db.close();
    }

    @Test
    public void storeWardrobeMissingImageTest() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new WardrobeDbHelper(InstrumentationRegistry.getTargetContext());
        Wardrobe.initInstance(((IStorageHelper)db));

        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem shirt = new WardrobeItem(BLUE_SHIRT, WardrobeItemType.SHIRT);
        IWardrobeItem trousers = new WardrobeItem(KHAKIS, WardrobeItemType.TROUSERS);
        wardrobe.addWardrobeItem(shirt);
        wardrobe.addWardrobeItem(trousers);

        db.storeWardrobe(wardrobe);
        db.close();
    }
}
