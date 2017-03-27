package com.example.jonatan.clothesplanner;

import android.content.Context;

import com.example.jonatan.clothesplanner.wardrobe.IFileHandlingHelper;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.IWeeklyPlan;
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

import static com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType.SHIRT;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

/**
 * Created by Jonatan on 2017-03-27.
 */

@RunWith(MockitoJUnitRunner.class)
public class WeeklyPlanUnitTest {

    private static final String BLUE_SHIRT = "blue shirt";
    private static final String KHAKIS = "khakis";

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

    @Test
    public void testNewWeeklyPlanIsEmpty() throws Exception {
        IWeeklyPlan weeklyPlan = new WeeklyPlan();
        assertTrue(weeklyPlan.isEmpty());
    }

    /*
    @Test
    public void testGenerateWeeklyPlan() throws Exception {
        IWardrobe wardrobe = Wardrobe.getInstance();
        IWardrobeItem shirt = new Shirt(BLUE_SHIRT);
        IWardrobeItem khakis = new Trousers(KHAKIS);
        wardrobe.addWardrobeItem(shirt);
        wardrobe.addWardrobeItem(khakis);

        IWeeklyPlan weeklyPlan = wardrobe.getWeeklyPlan();
        weeklyPlan.generateWeeklyPlan();
        assertFalse(weeklyPlan.isEmpty());

        assertEquals(shirt, weeklyPlan.getShirt(0));
        assertEquals(khakis, weeklyPlan.getTrousers(0));
    }
    */
}
