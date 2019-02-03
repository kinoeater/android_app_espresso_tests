package com.mytaxi.android_demo;


import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.mytaxi.android_demo.activities.EspressoIdlingResource;
import com.mytaxi.android_demo.activities.MainActivity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
//@LargeTest


public class call_driver {




    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;
    private String username = "crazydog335";
    private String correct_password = "venture";

    @Before
    public void registerIdlingResourceAndSetActivity() {
        mActivity = mActivityRule.getActivity();
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }


    @Test
    public void lets_call_a_driver()  {
        try {
            // Type email and password, then click login button
            onView(withId(R.id.edt_username)).perform(typeText(username),
                    closeSoftKeyboard());
            onView(withId(R.id.edt_password)).perform(typeText(correct_password),
                    closeSoftKeyboard());
            onView(withId(R.id.btn_login)).perform(click());

            // verify if the login is successful
            onView(withText("mytaxi demo"))
                    .check(matches(isDisplayed()));

        } catch (NoMatchingViewException e) {

            // catches the exp if the client is already logged in, alternatively if condition could be used

            Log.d("Skipping", "No login test performed! as it is already logged in");
        }

        onView(withId(R.id.searchContainer)).perform(click());
        onView(withId(R.id.textSearch)).perform(typeText("sa"),
                closeSoftKeyboard());

        onView(withText("Sarah Scott"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));


        onView(withText("Sarah Scott"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());


        onView(withText("Driver Profile"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.fab))
                .check(matches(isDisplayed()));

        onView(withId(R.id.fab)).perform(click());


    }

    @After
    public void end_of_main_test() {

        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

}
