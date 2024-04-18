package ru.shuevalov.metronome_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.shuevalov.metronome_project.databinding.SettingsFragmentBinding

class SettingsFragment : Fragment(R.layout.settings_fragment) {
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        TODO()
        return binding.root
    }
}