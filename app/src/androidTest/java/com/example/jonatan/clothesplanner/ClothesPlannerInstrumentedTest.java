package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
    static private String KHAKIS = "khakis";
    static private String BLUE_SHIRT = "blue shirt";
    private FileInputStream fileInputStream;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = appContext.openFileOutput(appContext.getResources().getString(R.string.saved_trousers), Context.MODE_PRIVATE);
            fileOutputStream.write(wardrobeItemStringToBeWrittenBeforeStart.getBytes());

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        IWardrobe wardrobe = Wardrobe.getInstance();
        wardrobe.clear();

        Context appContext = InstrumentationRegistry.getTargetContext();
        File trousersFile = new File(appContext.getFilesDir()+"/"+appContext.getResources().getString(R.string.saved_trousers));
        File shirtFile = new File(appContext.getFilesDir()+"/"+appContext.getResources().getString(R.string.saved_shirts));
        trousersFile.delete();
        shirtFile.delete();

    }

    @Test
    public void displayWeeklyPlanTest() throws Exception {
        //Click Login button
        onView(withId(R.id.LoginButton)).perform(click());

        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());

        // Add khakis items
        onView(withId(R.id.editText_add_item)).perform(typeText(KHAKIS), closeSoftKeyboard());
        //select trousers from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Trousers"))).perform(click());
        onView(withId(R.id.button)).perform(click());

        //add shirt item
        onView(withId(R.id.editText_add_item)).perform(typeText(BLUE_SHIRT), closeSoftKeyboard());
        //select trousers from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Shirt"))).perform(click());
        onView(withId(R.id.button)).perform(click());

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        //Click Weekly Plan button
        onView(withId(R.id.WeeklyPlanButton)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(hasDescendant(withText(MONDAY)), hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart)), hasDescendant(withText(BLUE_SHIRT))))));

        onView(withId(R.id.weekly_plan_layout))
                .check(matches(hasDescendant(allOf(hasDescendant(withText(TUESDAY)), hasDescendant(withText(KHAKIS))))));

        /*
        onView(withId(R.id.monday))
                .check(matches(hasDescendant(withText(BLUE_SHIRT))));
        */

        //Click on back button
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        InstrumentationRegistry.getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }


    @Test
    public void loadWardrobeFromFileOnStartupTest() throws Exception {
        //Click Login button
        onView(withId(R.id.LoginButton)).perform(click());

        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.trousers_layout))
                .check(matches(hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(wardrobeItemStringToBeWrittenBeforeStart);
        onView(allOf(withParent(withId(R.id.trousers_layout)), withText(wardrobeItemStringToBeWrittenBeforeStart))).check(doesNotExist());

        //Check that file has been cleared
        Context appContext = InstrumentationRegistry.getTargetContext();
        String wardrobeFileString = readLineFromWardrobeFile(appContext.getResources().getString(R.string.saved_trousers));
        assertNull(wardrobeFileString);
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.jonatan.clothesplanner", appContext.getPackageName());
    }


    @Test
    public void addRemoveWardrobeItemTest() throws Exception {
        //Click Login button
        onView(withId(R.id.LoginButton)).perform(click());

        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());

        // Add khakis items
        onView(withId(R.id.editText_add_item)).perform(typeText(KHAKIS), closeSoftKeyboard());
        //select trousers from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Trousers"))).perform(click());
        onView(withId(R.id.button)).perform(click());

        //Check that the editText field has been cleared
        onView(withId(R.id.editText_add_item)).check(matches(withText("")));

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.trousers_layout))
                .check(matches(hasDescendant(withText(KHAKIS))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(KHAKIS);

        onView(allOf(withParent(withId(R.id.trousers_layout)), withText(KHAKIS))).check(doesNotExist());
    }

    @Test
    public void addRemoveWardrobeItemReadFromFileTest() throws Exception {
        //Click Login button
        onView(withId(R.id.LoginButton)).perform(click());

        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());

        // Type text and then press the button.
        onView(withId(R.id.editText_add_item))
                .perform(typeText(KHAKIS), closeSoftKeyboard());
        //select trousers from the drop-down menu (spinner)
        onView(withId(R.id.wardrobe_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Trousers"))).perform(click());
        onView(withId(R.id.button)).perform(click());

        // Check that the item was written to file
        Context appContext = InstrumentationRegistry.getTargetContext();
        String inputString = readLineFromWardrobeFile(appContext.getResources().getString(R.string.saved_trousers));
        assertEquals(inputString, KHAKIS);

        //Click remove and check that item is removed from file
        clickRemove(KHAKIS);
        String secondInput = readLineFromWardrobeFile(appContext.getResources().getString(R.string.saved_trousers));

        assertNull(secondInput);
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
                        return "click plus button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
    }
}
