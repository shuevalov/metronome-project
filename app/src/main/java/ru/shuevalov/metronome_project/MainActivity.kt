package ru.shuevalov.metronome_project

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.shuevalov.metronome_project.databinding.ActivityMainBinding
import ru.shuevalov.metronome_project.fragments.MainMetronomeFragment
import ru.shuevalov.metronome_project.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_frag_container) as NavHostFragment
        val navController = navHostFragment.navController

//        supportFragmentManager.beginTransaction().add(R.id.main_frag_container, MainMetronomeFragment()).commit()

//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

//        setSupportActionBar(binding.toolbar)



//        binding.toolbar.setNavigationOnClickListener {
//            navController.navigate(R.id.action_mainMetronomeFragment_to_settingsFragment)
//        }
        
//        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)

    }
}