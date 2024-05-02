package ru.shuevalov.metronome_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.shuevalov.metronome_project.databinding.ActivityMainBinding
import ru.shuevalov.metronome_project.fragments.MainMetronomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.main_frag_container, MainMetronomeFragment())
            .commit()
        binding = ActivityMainBinding.inflate(layoutInflater)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.main_frag_container) as NavHostFragment
//        navController = navHostFragment.navController
//        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
    }
}