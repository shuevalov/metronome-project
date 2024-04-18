package ru.shuevalov.metronome_project

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
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
import ru.shuevalov.metronome_project.databinding.MainMetronomeFragmentBinding

class MainMetronomeFragment : Fragment(R.layout.main_metronome_fragment) {

    class Tick(val context: Context?, val id: Int) {
        val pool: SoundPool = SoundPool.Builder().build()
        val sound = pool.load(context, id, 1)
        val duration: Long = MediaPlayer.create(context, id).duration.toLong()

        fun play() {
            this.pool.play(sound, 1.0F, 1.0F, 1, 0, 1.0F)
        }
    }

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

        setBPM(120L)
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
            setBPM(bpm - 1)
        }
        binding.minusTenButton.setOnClickListener {
            setBPM(bpm - 10)
        }
        binding.plusOneButton.setOnClickListener {
            setBPM(bpm + 1)
        }
        binding.plusTenButton.setOnClickListener {
            setBPM(bpm + 10)
        }

        return binding.root
    }

    private fun setBPM(input: Long = -1L) {
        bpm = if (input == -1L) binding.testEditText.text.toString().toLong()
            else input
        delayBpm = (60000L / bpm) - 50 // temporary
        binding.testEditText.setText(bpm.toString())
    }
}