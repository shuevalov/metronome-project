package ru.shuevalov.metronome_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.shuevalov.metronome_project.R
import ru.shuevalov.metronome_project.databinding.MainMetronomeFragmentBinding
import ru.shuevalov.metronome_project.models.Tick

class MainMetronomeFragment : Fragment(R.layout.main_metronome_fragment) {

    private lateinit var binding: MainMetronomeFragmentBinding
    private lateinit var tick: Tick

    private val MY_TAG: String = "MainMetronomeFragment"
    private var bpm: Long = 0L
    // bpm into millis
    private var delayBpm: Long = 0L
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MainMetronomeFragmentBinding.inflate(inflater, container, false)
        tick = Tick(context, R.raw.vanilla_tick)

        var isTicking: Boolean = false
        // ask teacher
        binding.run.setOnClickListener {
            if (!isTicking) {
                job = lifecycleScope.launch {
                    isTicking = true
                    while (true)
                    {
                        Log.d(MY_TAG, "TICK")
                        tick.play()
                        delay(delayBpm)
                    }
                }
            } else {
                lifecycleScope.launch {
                    job?.cancelAndJoin()
                    isTicking = false
                }
            }
        }

        binding.bpmPicker.apply {
            minValue = 1
            maxValue = 300
            setOnValueChangedListener { picker, oldVal, newVal ->
                setBpm(newVal.toLong())
            }
            setOnLongClickListener {
                TODO("input a value, with dialog fragment i guess")
            }

        }
        binding.minusOneButton.setOnClickListener {
            setBpm(bpm - 1)
        }
        binding.minusTenButton.setOnClickListener {
            setBpm(bpm - 10)
        }
        binding.plusOneButton.setOnClickListener {
            setBpm(bpm + 1)
        }
        binding.plusTenButton.setOnClickListener {
            setBpm(bpm + 10)
        }

        binding.settingsButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frag_container, SettingsFragment())
                .commit()
        }
        setBpm(60L)
        return binding.root
    }

    private fun setBpm(input: Long) {
        bpm = input
        delayBpm = (60000L / bpm) //- 50 // temporary
        if (bpm.toInt() != binding.bpmPicker.value) {
            binding.bpmPicker.value = bpm.toInt()
        }
    }
}