package com.example.habittracker.habitList.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.database.Habit
import com.example.habittracker.database.HabitDatabaseDao
import com.example.habittracker.database.HabitType

class ListViewModel(database: HabitDatabaseDao) : ViewModel() {
    val habits = database.getAllHabits()

    val textSort = MutableLiveData<String>()

    val increaseSort = MutableLiveData<Boolean>()

    init {
        textSort.value = ""
        increaseSort.value = true
    }


    fun getFilteredHabits(habitType: HabitType): List<Habit> {
        var filteredHabits = listOf<Habit>()
        habits.value?.let {
            filteredHabits = it.filter { habit ->
                habit.type == habitType
                        && habit.name.startsWith(textSort.value!!)
            }
        }
        filteredHabits = if (increaseSort.value!!)
            filteredHabits.sortedBy { it.id }
        else
            filteredHabits.sortedByDescending { it.id }
        return filteredHabits
    }

}