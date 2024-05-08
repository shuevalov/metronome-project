package ru.shuevalov.metronome_project

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import ru.shuevalov.metronome_project.databinding.ActivityMainBinding
import ru.shuevalov.metronome_project.fragments.MainMetronomeFragment
import ru.shuevalov.metronome_project.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_frag_container) as NavHostFragment
        val navController = navHostFragment.navController


        binding.toolbar.setNavigationOnClickListener {
            navController.navigate(R.id.action_mainMetronomeFragment_to_settingsFragment)
        }

//        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)



    }
}