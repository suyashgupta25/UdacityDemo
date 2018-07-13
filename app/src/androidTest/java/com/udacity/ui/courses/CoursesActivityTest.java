package com.udacity.ui.courses;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.udacity.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by suyashg on 13/07/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
@SuppressWarnings("deprecation")
public class CoursesActivityTest {

    @Rule
    public ActivityTestRule<CoursesActivity> rule = new ActivityTestRule<>(CoursesActivity.class);
    private IdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource() {
        mIdlingResource = rule.getActivity().getCourseListIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void shouldBeAbleToLoadCourses() throws InterruptedException {
        onView(withId(R.id.rl_courses_list)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToScrollCourses() throws InterruptedException {
        onView(withId(R.id.rl_courses_list)).check(matches(isDisplayed()));
        onView(withId(R.id.rl_courses_list)).perform(swipeUp());
        onView(withId(R.id.rl_courses_list)).perform(swipeUp());
    }

    @Test
    public void shouldBeAbleToOpenDetails() throws InterruptedException {
        onView(withId(R.id.rl_courses_list)).check(matches(isDisplayed()));
        onView(withId(R.id.rl_courses_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.tv_full_course_desc)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
