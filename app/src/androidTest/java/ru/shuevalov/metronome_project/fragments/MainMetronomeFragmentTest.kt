package ru.shuevalov.metronome_project.fragments

import android.view.LayoutInflater
import android.view.View
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withParentIndex
import androidx.test.espresso.matcher.ViewMatchers.withResourceName
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.shuevalov.metronome_project.MainActivity
import ru.shuevalov.metronome_project.R
import ru.shuevalov.metronome_project.databinding.MainMetronomeFragmentBinding


@RunWith(AndroidJUnit4::class)
class MainMetronomeFragmentTest {
    private lateinit var fragment: MainMetronomeFragment
    private lateinit var binding: MainMetronomeFragmentBinding

    @Before
    fun setup() {
        fragment = MainMetronomeFragment()
        binding = MainMetronomeFragmentBinding.inflate(LayoutInflater.from(InstrumentationRegistry.getInstrumentation().targetContext)) //activityRule.activity))
        fragment.binding = binding
    }

    @Test
    fun testSetBpm() {
        // Arrange
        val scenario = launch(MainActivity::class.java)
        val initialBpm = 100L
        fragment.bpm = initialBpm

        scenario.onActivity { activity ->
            activity.supportFragmentManager.beginTransaction()
                .add(R.id.main_frag_container, fragment)
                .commitNow()
        }

        //val bpmPicker = onView(withParent(withIndex(withId(binding.bpmPicker.id), 0)))
        //val bpmPicker = onView(withIndex(withResourceName("numberpicker_input"), 0))
        val bpmPicker = onView(withResourceName("numberpicker_input"))

        // Act
        fragment.setCurrentBpm(250L)

        // Assert
        bpmPicker.check(matches(withText("250")))

        // Act
        fragment.setCurrentBpm(350L)

        // Assert
        bpmPicker.check(matches(withText("300")))

        // Act
        fragment.setCurrentBpm(20L)

        // Assert
        bpmPicker.check(matches(withText("30")))
    }

    private fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var currentIndex: Int = 0

            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                description.appendText(" ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}