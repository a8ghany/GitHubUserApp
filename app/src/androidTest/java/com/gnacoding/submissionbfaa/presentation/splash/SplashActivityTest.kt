package com.gnacoding.submissionbfaa.presentation.splash

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.gnacoding.submissionbfaa.R
import com.gnacoding.submissionbfaa.presentation.MainActivity
import com.gnacoding.submissionbfaa.presentation.screen.splash.SplashActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(SplashActivity::class.java)
    }

    @Test
    fun testVisibility() {
        onView(withId(R.id.github_logo)).check(matches(isDisplayed()))
        onView(withId(R.id.github_text)).check(matches(isDisplayed()))
        onView(withId(R.id.created_by)).check(matches(isDisplayed()))
    }

    private lateinit var scenario: ActivityScenario<SplashActivity>
    private val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)

    @get:Rule
    val activityRule = ActivityScenarioRule<SplashActivity>(intent)

    @Test
    fun testMoveFromSplashToMain() {
        scenario = activityRule.scenario
    }
}