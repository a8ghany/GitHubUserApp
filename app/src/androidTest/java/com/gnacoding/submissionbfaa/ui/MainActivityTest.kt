package com.gnacoding.submissionbfaa.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.gnacoding.submissionbfaa.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testHomeVisibility() {
        onView(withId(R.id.navigation_home)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.navigation_favorite)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_settings)).perform(click())
        onView(withId(R.id.navigation_settings)).check(matches(isDisplayed()))
    }
}