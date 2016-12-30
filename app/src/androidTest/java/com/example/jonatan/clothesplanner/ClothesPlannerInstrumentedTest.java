package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ClothesPlannerInstrumentedTest {

    static private String wardrobeItemStringToBeWrittenBeforeStart = "jeans";
    static private String wardrobeItemStringToBeAdded = "khakis";
    private FileInputStream fileInputStream;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    /*
    public void ClothesPlannerInstrumentedTest()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = appContext.openFileOutput(appContext.getResources().getString(R.string.wardrobe_view), Context.MODE_PRIVATE);
            fileOutputStream.write(wardrobeItemStringToBeWrittenBeforeStart.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */


    @Before
    public void populateWardrobeFile() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = appContext.openFileOutput(appContext.getResources().getString(R.string.wardrobe_view), Context.MODE_PRIVATE);
            fileOutputStream.write(wardrobeItemStringToBeWrittenBeforeStart.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void saveInstanceState() {
        Context appContext = InstrumentationRegistry.getTargetContext();

    }


    @Test
    public void loadWardrobeFromFileOnStartupTest() throws Exception {
        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.wardrobe_layout))
                .check(matches(hasDescendant(withText(wardrobeItemStringToBeWrittenBeforeStart))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(wardrobeItemStringToBeWrittenBeforeStart);
        onView(allOf(withParent(withId(R.id.wardrobe_layout)), withText(wardrobeItemStringToBeWrittenBeforeStart))).check(doesNotExist());

        //Check that file has been cleared
        String wardrobeFileString = readLineFromWardrobeFile();
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
        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());

        // Type text and then press the button.
        onView(withId(R.id.editText_add_item))
                .perform(typeText(wardrobeItemStringToBeAdded), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        // Check that item has been added to the wardrobe linear layout
        onView(withId(R.id.wardrobe_layout))
                .check(matches(hasDescendant(withText(wardrobeItemStringToBeAdded))));

        //Click remove button and check that the item does not exist anymore
        clickRemove(wardrobeItemStringToBeAdded);

        onView(allOf(withParent(withId(R.id.wardrobe_layout)), withText(wardrobeItemStringToBeAdded))).check(doesNotExist());
    }

    @Test
    public void addRemoveWardrobeItemReadFromFileTest() throws Exception {
        //Click My Wardrobe button
        onView(withId(R.id.WardrobeButton)).perform(click());

        // Type text and then press the button.
        onView(withId(R.id.editText_add_item))
                .perform(typeText(wardrobeItemStringToBeAdded), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        // Check that the item was written to file
        String inputString = readLineFromWardrobeFile();
        assertEquals(inputString, wardrobeItemStringToBeAdded);

        //Click remove and check that item is removed from file
        clickRemove(wardrobeItemStringToBeAdded);
        String secondInput = readLineFromWardrobeFile();

        assertNull(secondInput);
    }

    private String readLineFromWardrobeFile() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        fileInputStream = appContext.openFileInput(appContext.getResources().getString(R.string.wardrobe_view));
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
