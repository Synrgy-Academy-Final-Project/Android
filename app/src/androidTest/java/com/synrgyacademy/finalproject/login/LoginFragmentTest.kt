package com.synrgyacademy.finalproject.login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.synrgyacademy.finalproject.MainActivity
import com.synrgyacademy.finalproject.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
        activityScenario = activityRule.scenario
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun loginAndLogout() {
        // Click on the profile button in the bottom navigation
        onView(withId(R.id.profile_navigation)).perform(click())

        // Click on the login button in the profile screen
        onView(withId(R.id.btn_masuk)).perform(click())

        // Type in the email and password
        onView(withId(R.id.email_login_edit_text)).perform(
            typeText("aa7@backlav.com"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.password_login_edit_text)).perform(
            typeText("Kevin123!"),
            closeSoftKeyboard()
        )

        // Click on the login button
        onView(withId(R.id.login_btn)).perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.ticket_navigation)).check(matches(isDisplayed()))

        // Click on the profile button in the bottom navigation
        onView(withId(R.id.profile_navigation)).perform(click())

        // Click on the logout button
        onView(withId(R.id.btn_keluar)).perform(click())

        // Click on the "yes" button in the logout dialog
        onView(withText("Ya")).perform(click())

        // Check if the user is logged out
        onView(withId(R.id.btn_masuk)).check(matches(isDisplayed()))
    }
}