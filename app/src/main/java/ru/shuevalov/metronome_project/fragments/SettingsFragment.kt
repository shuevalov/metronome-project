package ru.shuevalov.metronome_project.fragments

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ru.shuevalov.metronome_project.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs, rootKey)

        val exitButton = preferenceManager.findPreference<Preference>("exit")
        exitButton?.setOnPreferenceClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frag_container, MainMetronomeFragment())
                .commit()
            return@setOnPreferenceClickListener true
        }
    }
}