package ru.shuevalov.metronome_project

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.shuevalov.metronome_project.models.Tick

import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class TickTest {

    private lateinit var tick: Tick
    private lateinit var mockSoundPool: SoundPool
    private var mockSoundId: Int = 0
    private lateinit var mockAudioManager: AudioManager

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mockSoundPool = mock()
        mockSoundId = 123
        mockAudioManager = mock()

        `when`(context.getSystemService(Context.AUDIO_SERVICE)).thenReturn(mockAudioManager)
        `when`(SoundPool.Builder().build()).thenReturn(mockSoundPool)
        `when`(mockSoundPool.load(context, mockSoundId, 1)).thenReturn(mockSoundId)

        tick = Tick(context, mockSoundId)
    }

    @Test
    fun testPlaySound() {
        tick.play()

        verify(mockSoundPool).play(mockSoundId, 1.0F, 1.0F, 1, 0, 1.0F)
    }
}