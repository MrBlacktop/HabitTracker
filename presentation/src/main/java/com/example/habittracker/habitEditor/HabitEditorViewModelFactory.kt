package com.example.habittracker.habitEditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.HabitInteractor


class HabitEditorViewModelFactory(private val interactor: HabitInteractor, private val id: String? = null): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HabitEditorViewModel::class.java)){
            return HabitEditorViewModel(interactor, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}