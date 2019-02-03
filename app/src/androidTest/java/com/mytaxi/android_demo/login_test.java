package com.mytaxi.android_demo;


import android.util.Log;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.EspressoIdlingResource;
import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class login_test {


    //*****Login test with correct and incorrect credentials ****//

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    private String username = "crazydog335";
    private String correct_password = "venture";
    private String wrong_password = "zature";

    @Before
    public void registerIdlingResourceAndSetActivity() {
        mActivity = mActivityRule.getActivity();
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void lets_login() {

        //******Trying to login with Correct credentials *************//

        Log.e("@Test", "Login success, play with drawer, then log out");

        onView(withId(R.id.edt_username)).perform(typeText(username),
                closeSoftKeyboard());
        onView(withId(R.id.edt_password)).perform(typeText(correct_password),
                closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        onView(withText("mytaxi demo"))
                .check(matches(isDisplayed()));

        onView(withContentDescription(R.string.navigation_drawer_open))
                .perform(click());

        onView(withText("Logout"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.fab))
                .perform(click());

        onView(withText("mytaxi demo"))
                .check(matches(isDisplayed()));

        onView(withContentDescription(R.string.navigation_drawer_open))
                .perform(click());

        onView(withText("Logout"))
                .perform(click());

    }


    @Test
    public void lets_cannot_login() {
        //******Trying to login with incorrect credentials *************//

        Log.e("@Test", "Login failure, verify fail message");

        onView(withId(R.id.edt_username)).perform(typeText(username),
                closeSoftKeyboard());
        onView(withId(R.id.edt_password)).perform(typeText(wrong_password),
                closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        onView(withText("Login failed"))
                .check(matches(isDisplayed()));

    }


    @After
    public void end_of_main_test() {

        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }



}
