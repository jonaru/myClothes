package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;

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
import java.io.IOException;
import java.io.InputStreamReader;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.jonatan.clothesplanner.matchers.EspressoTestsMatchers.noDrawable;
import static com.example.jonatan.clothesplanner.matchers.EspressoTestsMatchers.withBackground;
import static com.example.jonatan.clothesplanner.matchers.EspressoTestsMatchers.withDrawable;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
    private static final String SNEAKERS = "sneakers";
    static private int LOWER_SPINNER_OPTION = 0;
    static private int UPPER_SPINNER_OPTION = 1;
    private static final int FOOTWEAR_SPINNER_OPTION = 2;
    private String[] wardrobeSpinnerStrings;
    private FileInputStream fileInputStream;
    WardrobeDbHelper db;

    private static final boolean runWithDb = true;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<PopUpWardrobeActivity> mPopUpWardrobeActivityRule = new ActivityTestRule<>(PopUpWardrobeActivity.class);
    //public IntentsTestRule<PopUpWardrobeActivity> mPopUpWardrobeActivityRule = new IntentsTestRule<>(PopUpWardrobeActivity.class);

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        wardrobeSpinnerStrings = appContext.getResources().getStringArray(R.array.wardrobe_array);
        if (runWithDb) {
            db = new WardrobeDbHelper(InstrumentationRegistry.getTargetContext());
            Wardrobe.initInstance((db));
            IWardrobe wardrobe = Wardrobe.getInstance();

            //Store the jeans in the database before tests start
            Drawable jeans_drawable = ContextCompat.getDrawable(appContext, R.drawable.jeans_trousers);
            IWardrobeItem trousers = new WardrobeItem(wardrobeItemStringToBeWrittenBeforeStart, jeans_drawable, WardrobeItemType.LOWER);
            wardrobe.addWardrobeItem(trousers);
            db.storeWardrobe(wardrobe);
            wardrobe.clear();
        }
    }

    @After
    public void cleanUp() throws Exception{
        IWardrobe wardrobe = Wardrobe.getInstance();
        wardrobe.clear();
        Context appContext = InstrumentationRegistry.getTargetContext();

        if(runWithDb){
            db.clearTables();
        }
    }

    @Test
    public void scrollWeeklyPlanTest() throws Exception {
        enterWardrobe();
        addShirt(BLUE_SHIRT);

        //Click Weekly Plan button
        onView(withId(R.id.action_weekly_plan)).perform(click());

        //Scroll view
        onView(withId(R.id.fridayViewGroup)).
                perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    private void clickOnBackButton() {
        getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }

    @Test
    public void navigateFromWeeklyPlanToWardrobe() throws Exception {
        enterWardrobe();

        //Click Weekly Plan button
        onView(withId(R.id.action_weekly_plan)).perform(click());

        //check that we are in the weekly plan activity
        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(withId(R.id.mondayViewGroup))));

        //Click on wardrobe button
        onView(withId(R.id.action_wardrobe)).perform(click());

        //check that we are in the wardrobe activity
        onView(withId(R.id.lower_body_pager))
                .check(matches(hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart))));
    }

    @Test
    public void displayWeeklyPlanTest() throws Exception {
        enterWardrobe();

        // Add khakis item
        addTrousers(KHAKIS);

        //add shirt items
        addShirt(BLUE_SHIRT);
        addShirt(WHITE_SHIRT);
        addShirt(STRIPED_SHIRT);

        //Click Weekly Plan button
        onView(withId(R.id.action_weekly_plan)).perform(click());

        //Click Generate Weekly Plan button
        clickButtonTravis("Generate");

        // Check that items has been added to the plan
        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.mondayViewGroup), hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart)), hasDescendant(withDrawable(R.drawable.shirt_white))))));


        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.tuesdayViewGroup), hasDescendant(withDrawable(R.drawable.khaki_trousers)), hasDescendant(withDrawable(R.drawable.shirt_striped))))));

        //Click Generate Weekly Plan button
        clickButtonTravis("Generate");

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.mondayViewGroup), hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart)), hasDescendant(withDrawable(R.drawable.shirt_striped))))));

        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.tuesdayViewGroup), hasDescendant(withDrawable(R.drawable.khaki_trousers)), hasDescendant(withDrawable(R.drawable.shirt_blue))))));


        //Click on back button
        clickOnBackButton();

        //Now enter again to check that the plan is read from file and presented
        //Click Weekly Plan button
        /*
        onView(withId(R.id.action_weekly_plan)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.mondayViewGroup), hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart)), hasDescendant(withDrawable(R.drawable.shirt_white))))));

        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(withId(R.id.tuesdayViewGroup), hasDescendant(withDrawable(R.drawable.khaki_trousers)), hasDescendant(withDrawable(R.drawable.shirt_striped))))));
        */
    }


    @Test
    public void loadWardrobeFromFileOnStartupTest() throws Exception {
        enterWardrobe();

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.lower_body_pager))
                .check(matches(hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(wardrobeItemStringToBeWrittenBeforeStart);
        onView(allOf(withParent(withId(R.id.lower_body_pager)), withText(wardrobeItemStringToBeWrittenBeforeStart))).check(doesNotExist());


        //Click on back button
        clickOnBackButton();

        //Click on back button
        clickOnBackButton();

        //Check that file has been cleared
        if(runWithDb){
            Wardrobe wardrobe = Wardrobe.getInstance();
            wardrobe.loadWardrobe();
            assertNull(wardrobe.findWardrobeItem(wardrobeItemStringToBeWrittenBeforeStart));
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
        enterWardrobe();
        addShirt(BLUE_SHIRT);

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.upper_body_pager))
                .check(matches(hasDescendant(withDrawable(R.drawable.shirt_blue))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(R.drawable.shirt_blue);
        onView(allOf(withParent(withId(R.id.lower_body_pager)), withText(KHAKIS)))
                .check(doesNotExist());
    }

    @Test
    public void addRemoveFootWearTest() throws Exception {
        enterWardrobe();
        addFootwear(SNEAKERS);

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.footwear_pager))
                .check(matches(hasDescendant(withText(SNEAKERS))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(SNEAKERS);
        onView(allOf(withParent(withId(R.id.footwear_pager)), withText(SNEAKERS)))
                .check(doesNotExist());

        assertNull(Wardrobe.getInstance().findWardrobeItem(SNEAKERS));
    }

    @Test
    public void highlightSelectedItemTest() throws Exception {
        enterWardrobe();

        onView(withId(R.id.button)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button)).perform(click());

        //Select image and check that it's highlighted
        onView(withId(R.id.blueShirtButton)).perform(click());
        onView(withId(R.id.blueShirtButton)).check(matches(withBackground(R.drawable.highlight)));
        onView(withId(R.id.whiteShirtButton)).check(matches(not(withBackground(R.drawable.highlight))));

        //Select a new image and check that the new one is highlighted and the old one is not anymore
        onView(withId(R.id.whiteShirtButton)).perform(click());
        onView(withId(R.id.whiteShirtButton)).check(matches(withBackground(R.drawable.highlight)));
        onView(withId(R.id.blueShirtButton)).check(matches(not(withBackground(R.drawable.highlight))));
    }

    @Test
    public void selectFromGalleryTest() throws Exception {
        enterWardrobe();
        onView(allOf(withId(R.id.button), isDisplayed())).perform(ViewActions.scrollTo());
        onView(withId(R.id.button)).perform(click());

        //Create ActivityResult to return when clicking the gallery button
        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        int resId = R.drawable.shirt_blue;
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + resources.getResourcePackageName(resId)
                + '/' + resources.getResourceTypeName(resId)
                + '/' + resources.getResourceEntryName(resId));
        Intent resultData = new Intent();
        resultData.setData(imageUri);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(
                Activity.RESULT_OK, resultData);

        //Expect gallery intent and set it to return the ActivityResult from above
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri pictureDirectoryUri = Uri.parse(pictureDirectoryPath);
        Matcher<Intent> expectedIntent = allOf(hasAction(Intent.ACTION_PICK),
                hasData(pictureDirectoryUri));
        Intents.init();
        intending(expectedIntent).respondWith(result);

        //Click gallery button
        onView(withId(R.id.galleryButton)).perform(click());
        intended(expectedIntent);

        //Check the image is displayed
        //onView(withId(R.id.galleryImageView)).check(matches(withDrawable(R.drawable.shirt_blue)));
        onView(withId(R.id.galleryImageButton)).check(matches(not(noDrawable())));
        Intents.release();
        //Intents.assertNoUnverifiedIntents();

        //select shirt from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(wardrobeSpinnerStrings[UPPER_SPINNER_OPTION]))).perform(click());

        onView(withId(R.id.galleryImageButton)).perform(click());
        onView(withId(R.id.galleryImageButton)).check(matches(withBackground(R.drawable.highlight)));
        onView(withId(R.id.addItemButton)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.upper_body_pager))
                .check(matches(hasDescendant(not(noDrawable()))));
    }

    @Test
    public void selectFromCameraTest() throws Exception {
        enterWardrobe();
        onView(withId(R.id.button)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button)).perform(click());

        //Create ActivityResult to return when clicking the camera button
        //Return this icon bitmap as the photo taken
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);

        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(
                Activity.RESULT_OK, resultData);

        //Expect camera intent and set it to return the ActivityResult from above
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri pictureDirectoryUri = Uri.parse(pictureDirectoryPath);
        Matcher<Intent> expectedIntent = allOf(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));
        Intents.init();
        intending(expectedIntent).respondWith(result);

        //Click gallery button
        onView(withId(R.id.photoButton)).perform(click());
        intended(expectedIntent);

        //Check the image is displayed
        onView(withId(R.id.galleryImageButton)).check(matches(not(noDrawable())));
        Intents.release();

        //select shirt from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(wardrobeSpinnerStrings[UPPER_SPINNER_OPTION]))).perform(click());

        onView(withId(R.id.galleryImageButton)).perform(click());
        onView(withId(R.id.galleryImageButton)).check(matches(withBackground(R.drawable.highlight)));
        onView(withId(R.id.addItemButton)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.upper_body_pager))
                .check(matches(hasDescendant(not(noDrawable()))));
    }

    @Test
    public void addRemoveWardrobeItemReadFromFileTest() throws Exception {
        enterWardrobe();

        //Click remove button on the jeans
        clickRemove(wardrobeItemStringToBeWrittenBeforeStart);
        onView(allOf(withParent(withId(R.id.lower_body_pager)), withText(wardrobeItemStringToBeWrittenBeforeStart))).check(doesNotExist());

        addTrousers(KHAKIS);

        //Click on back button
        clickOnBackButton();

        //Click on back button
        clickOnBackButton();

        // Check that the item was written to file
        Context appContext = InstrumentationRegistry.getTargetContext(); //need this for the file reading
        if(runWithDb){
            Wardrobe wardrobe = Wardrobe.getInstance();
            wardrobe.clear();
            wardrobe.loadWardrobe();
            assertNotNull(wardrobe.findWardrobeItem(KHAKIS));
        }

        //Go to wardrobe again, so that we read from database
        enterWardrobe();

        //Click remove and check that item is removed from file
        clickRemove(R.drawable.khaki_trousers);

        //Click on back button
        clickOnBackButton();

        //Click on back button
        clickOnBackButton();

        if(runWithDb){
            Wardrobe wardrobe = Wardrobe.getInstance();
            wardrobe.loadWardrobe();
            assertNull(wardrobe.findWardrobeItem(KHAKIS));
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
        onView(allOf(withText(R.string.remove), withParent(withChild(withChild(withText(itemStringToRemove)))))).perform(
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
        onView(allOf(withText(R.string.remove), withParent(withChild(withChild(withDrawable(drawableToRemove)))))).perform(
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

    private void enterWardrobe() {
        //Click Login button
        onView(withId(R.id.LoginButton)).perform(click());
    }

    private void addShirt(String description) {
        onView(withId(R.id.button)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button)).perform(click());

        //select shirt from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(wardrobeSpinnerStrings[UPPER_SPINNER_OPTION]))).perform(click());

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
        onData(allOf(is(instanceOf(String.class)), is(wardrobeSpinnerStrings[LOWER_SPINNER_OPTION]))).perform(click());

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

    private void addFootwear(String description) {
        onView(withId(R.id.button)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button)).perform(click());

        //select shirt from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(wardrobeSpinnerStrings[FOOTWEAR_SPINNER_OPTION]))).perform(click());

        //Enter description
        onView(withId(R.id.editText_add_item)).perform(typeText(description), closeSoftKeyboard());

        /*
        //Select image
        if (description.compareTo(BLUE_SHIRT) == 0)
        {
            onView(withId(R.id.blueShirtButton)).perform(click());
        } else if (description.compareTo(WHITE_SHIRT) == 0) {
            onView(withId(R.id.whiteShirtButton)).perform(click());
        } else if (description.compareTo(STRIPED_SHIRT) == 0) {
            onView(withId(R.id.stripedShirtButton)).perform(click());
        }
        */

        //Click to add the new shoes
        onView(withId(R.id.addItemButton)).perform(click());
    }
}
