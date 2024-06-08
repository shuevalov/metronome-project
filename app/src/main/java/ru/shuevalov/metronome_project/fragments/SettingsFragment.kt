package ru.shuevalov.metronome_project.fragments

import android.app.LocaleManager
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.app.LocaleManagerCompat
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ru.shuevalov.metronome_project.R

class SettingsFragment : PreferenceFragmentCompat() {

    private var signed = false

    override fun onResume() {
        signed = Firebase.auth.currentUser != null
        if (signed) {
            val nickname = Firebase.auth.currentUser?.displayName
            findPreference<Preference>("account")?.title =
                nickname?.ifEmpty { getString(R.string.anon) }
                ?: getString(R.string.anon)
        } else {
            findPreference<Preference>("account")?.title = getString(R.string.account)
        }
        super.onResume()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar?.apply {
            setTitle(context.getString(R.string.settings))
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                parentFragmentManager.popBackStackImmediate()
            }
        }
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs, rootKey)
        signed = Firebase.auth.currentUser != null

        // account
        findPreference<Preference>("account")?.setOnPreferenceClickListener {
            findNavController().navigate(
                if (!signed)
                    R.id.action_settingsFragment_to_authorizationFragment
                else
                    R.id.action_settingsFragment_to_accountSettingsFragment
            )
            true
        }
        findPreference<ListPreference>("languages")?.setOnPreferenceChangeListener { preference, newValue ->
            AppCompatDelegate.setApplicationLocales(LocaleManagerCompat.getApplicationLocales(requireContext()))
            when (newValue) {
                "1"-> {
                    setLanguage("en")
                    Log.d("RRR", "english is chosen")
                }
//                "2" -> {
//                    setLanguage("de")
//                    Log.d("RRR", "german is chosen")
//                }
                "2" -> {
                    setLanguage("ru")
                    Log.d("RRR", "russian is chosen")
                }
                else -> {
                    setLanguage("en")
                    Log.d("RRR", "nothing is chosen")
                }
            }
            true
        }

        findPreference<ListPreference>("themes")?.setOnPreferenceChangeListener { preference, newValue ->
            when (newValue) {
                "1" -> {
                    activity?.setTheme(R.style.AppTheme_Dark)
                    recreateFragment()
                }
                "2" -> {
                    activity?.setTheme(R.style.AppTheme_Light)
                    recreateFragment()
                }
//                "3" -> {
//                    activity?.setTheme(R.style.AppTheme_Light)
//                }
            }
            true
        }
    }

    private fun setLanguage(newLang: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireContext().getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(newLang)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(newLang))
        }
    }
    private fun recreateFragment() {
        findNavController().navigate(
            R.id.settingsFragment,
            arguments,
            NavOptions.Builder()
                .setPopUpTo(R.id.settingsFragment, true)
                .build()
        )
    }
}