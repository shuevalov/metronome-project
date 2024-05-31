package ru.shuevalov.metronome_project.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
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
        val initialBpm = 100L
        fragment.bpm = initialBpm

        // Act
        fragment.setCurrentBpm(250L)

        val scenario = launch(MainActivity::class.java)

        scenario.onActivity {activity ->
            activity.supportFragmentManager.beginTransaction()
                .add(R.id.main_frag_container, fragment)
                .commitNow()
        }

        // Assert
        onView(withId(binding.bpmPicker.id)).check(matches(withSpinnerText(250)))

        // Act
        fragment.setCurrentBpm(350L)

        // Assert
        onView(withId(binding.bpmPicker.id)).check(matches(withSpinnerText(300)))

        // Act
        fragment.setCurrentBpm(20L)

        // Assert
        onView(withId(binding.bpmPicker.id)).check(matches(withSpinnerText(30)))
    }
}