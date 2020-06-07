package com.example.habittracker.habitList.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.HabitInteractor
import javax.inject.Inject

class ListViewModelFactory @Inject constructor(private val processingService: HabitInteractor) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(processingService) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}