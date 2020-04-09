package com.example.habittracker.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.database.HabitDatabaseDao

class HabitViewModelFactory(private val dataSource: HabitDatabaseDao, private val id: Long? = null): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HabitViewModel::class.java)){
            return HabitViewModel(dataSource, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}