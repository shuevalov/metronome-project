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
    private var delayBpm: Long = 0L
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainMetronomeFragmentBinding.inflate(inflater, container, false)
        tick = Tick(context, R.raw.vanilla_tick)

        setBpm(120L)
        var isTicking: Boolean = false
        binding.run.setOnClickListener {
            if (!isTicking) {
                job = lifecycleScope.launch {
                    isTicking = true
                    while (true)
                    {
                        Log.d(MY_TAG, "L")
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

        return binding.root
    }

    // if there's no argument - set bpm value from EditText
    private fun setBpm(input: Long = -1L) {
        bpm = if (input == -1L) binding.testEditText.text.toString().toLong()
            else input
        delayBpm = (60000L / bpm) - 50 // temporary
        binding.testEditText.setText(bpm.toString())
    }
}