package com.example.habittracker.habitEditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.database.HabitDatabaseDao

class HabitEditorViewModelFactory(private val dataSource: HabitDatabaseDao, private val id: Long? = null): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HabitEditorViewModel::class.java)){
            return HabitEditorViewModel(dataSource, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}