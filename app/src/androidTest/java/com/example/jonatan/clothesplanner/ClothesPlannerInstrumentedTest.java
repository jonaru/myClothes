package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.KeyEvent;
import android.view.View;

import com.example.jonatan.clothesplanner.wardrobe.IStorageAdapter;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobedb.WardrobeDbHelper;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.jonatan.clothesplanner.matchers.EspressoTestsMatchers.withDrawable;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ClothesPlannerInstrumentedTest {

    private static final String MONDAY = "Monday";
    private static final String TUESDAY = "Tuesday";
    static private String wardrobeItemStringToBeWrittenBeforeStart = "jeans";
    static private String JEANS = "jeans";
    static private String KHAKIS = "khakis";
    static private String BLUE_SHIRT = "blue shirt";
    static private String WHITE_SHIRT = "white shirt";
    static private String STRIPED_SHIRT = "striped shirt";
    private FileInputStream fileInputStream;
    WardrobeDbHelper db;

    private static final boolean runWithDb = true;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<PopUpWardrobeActivity> mPopUpWardrobeActivityRule = new ActivityTestRule<>(PopUpWardrobeActivity.class);

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        if (runWithDb) {
            db = new WardrobeDbHelper(InstrumentationRegistry.getTargetContext());
            Wardrobe.initInstance((db));
            IWardrobe wardrobe = Wardrobe.getInstance();

            //Store the jeans in the database before tests start
            Drawable jeans_drawable = ContextCompat.getDrawable(appContext, R.drawable.jeans_trousers);
            IWardrobeItem trousers = new WardrobeItem(wardrobeItemStringToBeWrittenBeforeStart, jeans_drawable, WardrobeItemType.TROUSERS);
            wardrobe.addWardrobeItem(trousers);
            db.storeWardrobe(wardrobe);
            wardrobe.clear();
        }
        else {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = appContext.openFileOutput(appContext.getResources().getString(R.string.saved_trousers), Context.MODE_PRIVATE);
                fileOutputStream.write(wardrobeItemStringToBeWrittenBeforeStart.getBytes());

                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void cleanUp() {
        IWardrobe wardrobe = Wardrobe.getInstance();
        wardrobe.clear();
        Context appContext = InstrumentationRegistry.getTargetContext();

        if(runWithDb){
            db.clearTables();
        } else {
            File trousersFile = new File(appContext.getFilesDir()+"/"+appContext.getResources().getString(R.string.saved_trousers));
            File shirtFile = new File(appContext.getFilesDir()+"/"+appContext.getResources().getString(R.string.saved_shirts));
            File weeklyPlanFile = new File(appContext.getFilesDir()+"/"+appContext.getResources().getString(R.string.weekly_plan));
            trousersFile.delete();
            shirtFile.delete();
            weeklyPlanFile.delete();
        }
    }

    @Test
    public void scrollWeeklyPlanTest() throws Exception {
        goToWardrobe();
        addShirt(BLUE_SHIRT);

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        //Click Weekly Plan button
        onView(withId(R.id.WeeklyPlanButton)).perform(click());

        //Scroll view
        onView(withId(R.id.fridayViewGroup)).
                perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void displayWeeklyPlanTest() throws Exception {
        goToWardrobe();

        // Add khakis item
        addTrousers(KHAKIS);

        //add shirt items
        addShirt(BLUE_SHIRT);
        addShirt(WHITE_SHIRT);
        addShirt(STRIPED_SHIRT);

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        //Click Weekly Plan button
        onView(withId(R.id.WeeklyPlanButton)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.mondayViewGroup), hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart)), hasDescendant(withDrawable(R.drawable.shirt_blue))))));


        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.tuesdayViewGroup), hasDescendant(withDrawable(R.drawable.khaki_trousers)), hasDescendant(withDrawable(R.drawable.shirt_white))))));

        //Click Generate Weekly Plan button
        clickButtonTravis("Generate");

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.mondayViewGroup), hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart)), hasDescendant(withDrawable(R.drawable.shirt_white))))));

        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.tuesdayViewGroup), hasDescendant(withDrawable(R.drawable.khaki_trousers)), hasDescendant(withDrawable(R.drawable.shirt_striped))))));


        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        //Now enter again to check that the plan is read from file and presented
        //Click Weekly Plan button
        onView(withId(R.id.WeeklyPlanButton)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.mondayViewGroup), hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart)), hasDescendant(withDrawable(R.drawable.shirt_white))))));

        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.tuesdayViewGroup), hasDescendant(withDrawable(R.drawable.khaki_trousers)), hasDescendant(withDrawable(R.drawable.shirt_striped))))));
    }


    @Test
    public void loadWardrobeFromFileOnStartupTest() throws Exception {
        goToWardrobe();

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.trousers_pager))
                .check(matches(hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(wardrobeItemStringToBeWrittenBeforeStart);
        onView(allOf(withParent(withId(R.id.trousers_pager)), withText(wardrobeItemStringToBeWrittenBeforeStart))).check(doesNotExist());


        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        //Check that file has been cleared
        if(runWithDb){
            Wardrobe wardrobe = Wardrobe.getInstance();
            wardrobe.loadWardrobe();
            assertNull(wardrobe.findWardrobeItem(wardrobeItemStringToBeWrittenBeforeStart));
        } else {
            Context appContext = InstrumentationRegistry.getTargetContext();
            String wardrobeFileString = readLineFromWardrobeFile(appContext.getResources().getString(R.string.saved_trousers));
            assertNull(wardrobeFileString);
        }
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.vikingtech.wardrober", appContext.getPackageName());
    }


    @Test
    public void addRemoveWardrobeItemTest() throws Exception {
        goToWardrobe();
        addShirt(BLUE_SHIRT);

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.shirt_pager))
                .check(matches(hasDescendant(withDrawable(R.drawable.shirt_blue))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(R.drawable.shirt_blue);
        onView(allOf(withParent(withId(R.id.trousers_pager)), withText(KHAKIS)))
                .check(doesNotExist());
    }

    @Test
    public void highlightSelectedItemTest() throws Exception {
        goToWardrobe();

        onView(withId(R.id.button)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button)).perform(click());

        //Select image
        Context appContext = InstrumentationRegistry.getTargetContext();
        //Drawable backgroundHighlight = ResourcesCompat.getDrawable(appContext.getResources(), R.drawable.highlight, null);
        onView(withId(R.id.blueShirtButton)).perform(click());
        View clickedView = mPopUpWardrobeActivityRule.getActivity().findViewById(R.id.blueShirtButton);
        assertNotNull(clickedView.getBackground());

        //Select a new image and check that the new one is highlighted and the old one is not anymore
        onView(withId(R.id.whiteShirtButton)).perform(click());
        View secondClickedView = mPopUpWardrobeActivityRule.getActivity().findViewById(R.id.whiteShirtButton);
        assertNotNull(secondClickedView.getBackground());
    }


    @Test
    public void addRemoveWardrobeItemReadFromFileTest() throws Exception {
        goToWardrobe();

        //Click remove button on the jeans
        clickRemove(wardrobeItemStringToBeWrittenBeforeStart);
        onView(allOf(withParent(withId(R.id.trousers_pager)), withText(wardrobeItemStringToBeWrittenBeforeStart))).check(doesNotExist());

        addTrousers(KHAKIS);

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        // Check that the item was written to file
        Context appContext = InstrumentationRegistry.getTargetContext(); //need this for the file reading
        if(runWithDb){
            Wardrobe wardrobe = Wardrobe.getInstance();
            wardrobe.clear();
            wardrobe.loadWardrobe();
            assertNotNull(wardrobe.findWardrobeItem(KHAKIS));
        } else {
            String inputString = readLineFromWardrobeFile(appContext.getResources().getString(R.string.saved_trousers));
            assertEquals(inputString, KHAKIS);
        }

        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());

        //Click remove and check that item is removed from file
        clickRemove(R.drawable.khaki_trousers);

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        if(runWithDb){
            Wardrobe wardrobe = Wardrobe.getInstance();
            wardrobe.loadWardrobe();
            assertNull(wardrobe.findWardrobeItem(KHAKIS));
        } else {
            String secondInput = readLineFromWardrobeFile(appContext.getResources().getString(R.string.saved_trousers));
            assertNull(secondInput);
        }
    }

    private String readLineFromWardrobeFile(String wardrobeFileString) throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        fileInputStream = appContext.openFileInput(wardrobeFileString);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String inputString = bufferedReader.readLine();
        fileInputStream.close();
        return inputString;
    }

    private void clickRemove(String itemStringToRemove) {
        onView(allOf(withText(R.string.remove), withParent(withChild(withText(itemStringToRemove))))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
    }

    private void clickRemove(int drawableToRemove) {
        onView(allOf(withText(R.string.remove), withParent(withChild(withDrawable(drawableToRemove))))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
    }

    private void clickButtonTravis(String buttonToClick) {
        onView(withText(buttonToClick)).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
    }

    private void goToWardrobe() {
        //Click Login button
        onView(withId(R.id.LoginButton)).perform(click());

        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());
    }

    private void addShirt(String description) {
        onView(withId(R.id.button)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button)).perform(click());

        //select shirt from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Shirt"))).perform(click());

        //Enter description
        onView(withId(R.id.editText_add_item)).perform(typeText(description), closeSoftKeyboard());
        //Select image
        if (description.compareTo(BLUE_SHIRT) == 0)
        {
            onView(withId(R.id.blueShirtButton)).perform(click());
        } else if (description.compareTo(WHITE_SHIRT) == 0) {
            onView(withId(R.id.whiteShirtButton)).perform(click());
        } else if (description.compareTo(STRIPED_SHIRT) == 0) {
            onView(withId(R.id.stripedShirtButton)).perform(click());
        }

        //Click to add the new shirt
        onView(withId(R.id.addItemButton)).perform(click());
    }

    private void addTrousers(String description) {
        onView(withId(R.id.button)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button)).perform(click());

        //select trousers from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Trousers"))).perform(click());

        //Enter description
        onView(withId(R.id.editText_add_item)).perform(typeText(description), closeSoftKeyboard());
        //Select image...
        if (description.compareTo(KHAKIS) == 0)
        {
            onView(withId(R.id.khakiTrouserstButton)).perform(click());
        } else if (description.compareTo(wardrobeItemStringToBeWrittenBeforeStart) == 0) {
            onView(withId(R.id.jeansTrouserstButton)).perform(click());
        } else if (description.compareTo(JEANS) == 0) {
            onView(withId(R.id.jeansTrouserstButton)).perform(click());
        }

        //Click to add the new trousers
        onView(withId(R.id.addItemButton)).perform(click());
    }
}
