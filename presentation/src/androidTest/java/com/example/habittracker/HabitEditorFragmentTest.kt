package com.example.habittracker

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habittracker.habitEditor.HabitEditorFragment
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HabitEditorFragmentTest(){

    @Test
    fun initialStateTest(){
        launchFragmentInContainer<HabitEditorFragment>(Bundle(),R.style.AppTheme)
        onView(withId(R.id.nameField)).check(matches(withText("")))
        onView(withId(R.id.descriptionField)).check(matches(withText("")))
        onView(withId(R.id.prioritySpinner)).check(matches(withSpinnerText("High")))
        onView(withId(R.id.first)).check(matches(isChecked()))
        onView(withId(R.id.timesField)).check(matches(withText("0")))
        onView(withId(R.id.periodicityField)).check(matches(withText("0")))
        onView(withId(R.id.deleteButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()))
    }
}