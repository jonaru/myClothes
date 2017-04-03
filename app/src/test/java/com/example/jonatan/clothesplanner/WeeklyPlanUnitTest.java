package com.example.jonatan.clothesplanner;

import android.content.Context;

import com.example.jonatan.clothesplanner.wardrobe.IFileHandlingHelper;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.WeeklyPlan;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Shirt;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.Trousers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
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
    IFileHandlingHelper mFileHandlingHelper;

    @InjectMocks
    Wardrobe wardrobe;

    @Before
    public void initSingletons() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mFileHandlingHelper).loadWardrobe(mWardrobe);
        Wardrobe.initInstance(mContext);
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
        IWardrobeItem blue_shirt = new Shirt(BLUE_SHIRT);
        IWardrobeItem white_shirt = new Shirt(WHITE_SHIRT);
        IWardrobeItem striped_shirt = new Shirt(STRIPED_SHIRT);
        IWardrobeItem jeans = new Trousers(JEANS);
        IWardrobeItem khakis = new Trousers(KHAKIS);
        wardrobe.addWardrobeItem(blue_shirt);
        wardrobe.addWardrobeItem(white_shirt);
        wardrobe.addWardrobeItem(striped_shirt);
        wardrobe.addWardrobeItem(jeans);
        wardrobe.addWardrobeItem(khakis);

        int[] indices = new int[2];
        indices[0] = 0;
        indices[1] = 0;

        wardrobe.setFileHandlingHelper(mFileHandlingHelper);
        doReturn(indices).when(mFileHandlingHelper).loadWeeklyPlanIndex();
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
