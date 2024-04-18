package ru.shuevalov.metronome_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.main_frag_container, MainMetronomeFragment()).commit()
    }
}