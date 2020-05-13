package com.example.habittracker.habitEditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.database.HabitDatabaseDao
import com.example.habittracker.network.HabitRepository

class HabitEditorViewModelFactory(private val repository: HabitRepository, private val id: String? = null): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HabitEditorViewModel::class.java)){
            return HabitEditorViewModel(repository, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}