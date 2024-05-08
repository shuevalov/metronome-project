package ru.shuevalov.metronome_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.appbar.MaterialToolbar
import ru.shuevalov.metronome_project.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
//        toolbar.navigationIcon =
//        set icon

        val navController = findNavController()

        // дальше не работает нужно разобраться
//        toolbar.setNavigationOnClickListener {
//            navController.navigate(R.id.action_settingsFragment_to_mainMetronomeFragment)
//        }
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs, rootKey)

        val exitButton = preferenceManager.findPreference<Preference>("exit")
//        exitButton?.setOnPreferenceClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.main_frag_container, MainMetronomeFragment())
//                .commit()
//            return@setOnPreferenceClickListener true
//        }


    }
}