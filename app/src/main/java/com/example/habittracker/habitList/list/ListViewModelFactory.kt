package com.example.habittracker.habitList.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.database.HabitDatabaseDao

class ListViewModelFactory(private val dataSource: HabitDatabaseDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(dataSource) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}