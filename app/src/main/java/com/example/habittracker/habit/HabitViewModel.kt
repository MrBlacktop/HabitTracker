package com.example.habittracker.habit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.database.Habit
import com.example.habittracker.database.HabitDatabaseDao
import kotlinx.coroutines.*

class HabitViewModel(dataSource: HabitDatabaseDao, habitId: Long? = null) : ViewModel() {
    var currentHabit: Habit
        private set

    val database: HabitDatabaseDao = dataSource
    private var isNewHabit = true


    init {
        if (habitId != null) {
            currentHabit = database.get(habitId)
            isNewHabit = false
        } else
            currentHabit = Habit()
    }

    private val _navigateToHabitList = MutableLiveData<Boolean?>()
    val navigateToHabitList: LiveData<Boolean?>
        get() = _navigateToHabitList


    fun saveButtonClicked() {

        if (isNewHabit)
            database.insert(currentHabit)
        else
            database.update(currentHabit)
        _navigateToHabitList.value = true
    }


    fun doneNavigating() {
        _navigateToHabitList.value = null
    }


}
