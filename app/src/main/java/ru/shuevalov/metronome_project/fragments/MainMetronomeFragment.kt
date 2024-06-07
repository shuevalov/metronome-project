package ru.shuevalov.metronome_project.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.shuevalov.metronome_project.R
import ru.shuevalov.metronome_project.databinding.MainMetronomeFragmentBinding
import ru.shuevalov.metronome_project.models.Tick

class MainMetronomeFragment : Fragment(R.layout.main_metronome_fragment) {

    lateinit var binding: MainMetronomeFragmentBinding
    private lateinit var tick: Tick
    private lateinit var prefs: SharedPreferences
    var bpm: Long = 60
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainMetronomeFragmentBinding.inflate(inflater, container, false)
        prefs = requireActivity().getSharedPreferences("file", MODE_PRIVATE)
        setCurrentBpm(prefs.getLong("bpm", 60))
        tick = Tick(context, R.raw.vanilla_tick)

        var isTicking: Boolean = false

        binding.run.setOnClickListener {
            if (!isTicking) {
                binding.run.text = "STOP"
                job = lifecycleScope.launch(Dispatchers.IO) {
                    isTicking = true
                    while (true) {
                        launch(Dispatchers.Main) {
                            tick.play()
                            Log.d("RRR", "TICK")
                        }
                        delay(60000 / bpm)
                    }
                }
            } else {
                lifecycleScope.launch {
                    binding.run.text = "RUN"
                    job?.cancelAndJoin()
                    isTicking = false
                }
            }
        }

        binding.bpmPicker.apply {
            minValue = 30
            maxValue = 300
            wrapSelectorWheel = false

            setOnValueChangedListener { picker, oldVal, newVal ->
                setCurrentBpm(newVal.toLong())
            }
        }
        binding.minusOneButton.setOnClickListener {
            setCurrentBpm(bpm - 1)
        }
        binding.minusTenButton.setOnClickListener {
            setCurrentBpm(bpm - 10)
        }
        binding.plusOneButton.setOnClickListener {
            setCurrentBpm(bpm + 1)
        }
        binding.plusTenButton.setOnClickListener {
            setCurrentBpm(bpm + 10)
        }
        binding.tapButton.setOnClickListener {
            tick.play()
        }
        //setBpm(60L)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setCurrentBpm(prefs.getLong("bpm", 100))
    }

    override fun onPause() {
        super.onPause()
        prefs.edit().putLong("bpm", bpm).apply()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d("RRR", "before set nav")
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar?.apply {
            setTitle("metronome")
            setNavigationIcon(R.drawable.ic_settings)
            setNavigationOnClickListener {
                job?.cancel()
                findNavController().navigate(R.id.action_mainMetronomeFragment_to_settingsFragment)
            }
        }
//        Log.d("RRR", "after set nav")
    }

    // set bpm and number picker values
    fun setCurrentBpm(newValue: Long) {
        bpm = when {
            newValue > 300 -> 300
            newValue < 30 -> 30
            else -> newValue
        }
        if (binding.bpmPicker.value != bpm.toInt()) {
            binding.bpmPicker.value = bpm.toInt()
        }
    }
}