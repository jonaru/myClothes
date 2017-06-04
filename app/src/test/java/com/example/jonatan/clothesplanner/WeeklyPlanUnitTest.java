package com.example.jonatan.clothesplanner;

import android.content.Context;

import com.example.jonatan.clothesplanner.wardrobe.IStorageAdapter;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.WeeklyPlan;
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

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Jonatan on 2017-03-27.
 */

@RunWith(MockitoJUnitRunner.class)
public class WeeklyPlanUnitTest {

    private static final String BLUE_SHIRT = "blue shirt";
    private static final String WHITE_SHIRT = "white shirt";
    private static final String STRIPED_SHIRT = "striped shirt";
    private static final String KHAKIS = "khakis";
    private static final String JEANS = "jeans";
    private static final String SHIRT_STRING = "Shirt";
    //private static int[] indices = new int[];

    @Mock
    Context mContext;

    @Mock
    IWardrobe mWardrobe;

    @Mock
    IStorageAdapter mStorageHelper;

    @InjectMocks
    Wardrobe wardrobe;

    @Before
    public void initSingletons() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mStorageHelper).loadWardrobe(mWardrobe);
        Wardrobe.initInstance(mStorageHelper);
    }

    @After
    public void cleanUp() {
        IWardrobe wardrobe = Wardrobe.getInstance();
        wardrobe.clear();
    }

    /*
    @Test
    public void testNewWeeklyPlanIsEmpty() throws Exception {
        IWeeklyPlan weeklyPlan = new WeeklyPlan();
        assertTrue(weeklyPlan.isEmpty());
    }
    */

    @Test
    public void testGenerateWeeklyPlan() throws Exception {
        Wardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem blue_shirt = new WardrobeItem(BLUE_SHIRT, WardrobeItemType.SHIRT);
        IWardrobeItem white_shirt = new WardrobeItem(WHITE_SHIRT, WardrobeItemType.SHIRT);
        IWardrobeItem striped_shirt = new WardrobeItem(STRIPED_SHIRT, WardrobeItemType.SHIRT);
        IWardrobeItem jeans = new WardrobeItem(JEANS, WardrobeItemType.TROUSERS);
        IWardrobeItem khakis = new WardrobeItem(KHAKIS, WardrobeItemType.TROUSERS);
        wardrobe.addWardrobeItem(blue_shirt);
        wardrobe.addWardrobeItem(white_shirt);
        wardrobe.addWardrobeItem(striped_shirt);
        wardrobe.addWardrobeItem(jeans);
        wardrobe.addWardrobeItem(khakis);

        int[] indices = new int[2];
        indices[0] = 0;
        indices[1] = 0;

        wardrobe.setStorageAdapter(mStorageHelper);
        doReturn(indices).when(mStorageHelper).loadWeeklyPlanIndex();
        WeeklyPlan weeklyPlan = new WeeklyPlan();

        assertEquals(blue_shirt, weeklyPlan.getShirt());
        assertEquals(jeans, weeklyPlan.getTrousers());
        assertEquals(white_shirt, weeklyPlan.getShirt());
        assertEquals(khakis, weeklyPlan.getTrousers());
        assertEquals(striped_shirt, weeklyPlan.getShirt());
        assertEquals(jeans, weeklyPlan.getTrousers());

        //generate a new plan to check that we get a different shirt
        weeklyPlan.generateWeeklyPlan();
        assertEquals(white_shirt, weeklyPlan.getShirt());
        assertEquals(jeans, weeklyPlan.getTrousers());
    }
}
