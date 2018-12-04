package com.example.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.android.bakingapp.Activity.DetailActivity;
import com.example.android.bakingapp.Activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static final String RECIPE_NAME = "Brownies";

    private IdlingResource idlingResource;



    @Rule public IntentsTestRule<MainActivity> mainActivityActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void beforeIdlingResource(){
        idlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }
    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickRecipeListItem_OpensDetailActivity() {
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(IntentMatchers.hasComponent(DetailActivity.class.getName()));
    }


    @Test
    public void click_recipe_header_test(){
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.detail_name_tv)).check(matches(isDisplayed())).check(matches(withText(RECIPE_NAME)));

    }
    @After
    public void unregisterIdlingResource(){
        if(idlingResource!=null){
            IdlingRegistry.getInstance().unregister(idlingResource);

        }
    }
}

