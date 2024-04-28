package ru.shuevalov.metronome_project.models

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool

class Tick(val context: Context?, val id: Int) {
    val pool: SoundPool = SoundPool.Builder().build()
    val sound = pool.load(context, id, 1)
    val duration: Long = MediaPlayer.create(context, id).duration.toLong()

    fun play() {
        this.pool.play(sound, 1.0F, 1.0F, 1, 0, 1.0F)
    }
}