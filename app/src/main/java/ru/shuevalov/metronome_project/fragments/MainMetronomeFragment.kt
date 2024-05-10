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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.shuevalov.metronome_project.BpmViewModel
import ru.shuevalov.metronome_project.R
import ru.shuevalov.metronome_project.databinding.MainMetronomeFragmentBinding
import ru.shuevalov.metronome_project.models.Tick

class MainMetronomeFragment : Fragment(R.layout.main_metronome_fragment) {

    private lateinit var binding: MainMetronomeFragmentBinding
    private lateinit var tick: Tick
    private lateinit var bpmObserver: Observer<Long>
    private val bpmVM: BpmViewModel by viewModels()
    private var job: Job? = null
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainMetronomeFragmentBinding.inflate(inflater, container, false)
        prefs = requireActivity().getSharedPreferences("file", MODE_PRIVATE)
        bpmVM.currentBpm.value = prefs.getLong("bpm", 30)
        tick = Tick(context, R.raw.vanilla_tick)

        var isTicking: Boolean = false
        // ask teacher
        binding.run.setOnClickListener {
            if (!isTicking) {
                job = lifecycleScope.launch {
                    isTicking = true
                    while (true) {
                        //Log.d(MY_TAG, "TICK")
                        tick.play()
                        delay(60000 / bpmVM.currentBpm.value!!)
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
            minValue = 30
            maxValue = 300
            wrapSelectorWheel = false

            bpmObserver = Observer { newBpm ->
                if (newBpm.toInt() != value) value = newBpm.toInt()
            }
            bpmVM.currentBpm.observeForever(bpmObserver)

            setOnValueChangedListener { picker, oldVal, newVal ->
                bpmVM.currentBpm.value = newVal.toLong()
            }
            setOnLongClickListener {
                TODO("input a value, with dialog fragment i guess")
            }
        }
        binding.minusOneButton.setOnClickListener {
            bpmVM.currentBpm.value!!.plus(1)
        }
        binding.minusTenButton.setOnClickListener {
            bpmVM.currentBpm.value!!.plus(10)
        }
        binding.plusOneButton.setOnClickListener {
            bpmVM.currentBpm.value!!.minus(1)
        }
        binding.plusTenButton.setOnClickListener {
            bpmVM.currentBpm.value!!.minus(10)
        }
        //setBpm(60L)
        return binding.root
    }


    override fun onPause() {
        super.onPause()
        prefs.edit().putLong("bpm", bpmVM.currentBpm.value!!).apply()
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
                findNavController().navigate(R.id.action_mainMetronomeFragment_to_settingsFragment)
            }
        }
//        Log.d("RRR", "after set nav")
    }
}