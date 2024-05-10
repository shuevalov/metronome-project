package ru.shuevalov.metronome_project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// я хуй знает как сюда присобачить хоть какой то арх паттерн ну
class BpmViewModel : ViewModel() {
    val currentBpm: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }
}