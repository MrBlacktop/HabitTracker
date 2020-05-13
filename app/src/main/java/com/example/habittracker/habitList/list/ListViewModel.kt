package com.example.habittracker.habitList.list

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.database.Habit
import com.example.habittracker.database.HabitDatabaseDao
import com.example.habittracker.database.HabitType
import com.example.habittracker.network.HabitApi
import com.example.habittracker.network.HabitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ListViewModel(database: HabitDatabaseDao, webToken: String) : ViewModel() {

    private val repository = HabitRepository(database, webToken)

    val habits = repository.getHabits()

    val textSort = MutableLiveData<String>()

    val increaseSort = MutableLiveData<Boolean>()

    private val uiScope = CoroutineScope(Dispatchers.Main)

    private val _eventNetworkError = MutableLiveData<Boolean>()
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    init {
        synchronize()
        textSort.value = ""
        increaseSort.value = true
    }


    fun getFilteredHabits(habitType: HabitType): List<Habit> {
        var filteredHabits = listOf<Habit>()
        habits.value?.let {
            filteredHabits = it.filter { habit ->
                habit.type == habitType
                        && habit.title.startsWith(textSort.value!!)
            }
        }
        filteredHabits = if (increaseSort.value!!)
            filteredHabits.sortedBy { it.uid }
        else
            filteredHabits.sortedByDescending { it.uid }
        return filteredHabits
    }

    private fun synchronize() {
        uiScope.launch {
            try {
                repository.synchronize()
                _eventNetworkError.value = false
            } catch (networkException: IOException) {
                _eventNetworkError.value = true
            }

        }
    }

    fun deleteHabit(habit: Habit) {
        uiScope.launch {
            try {
                repository.deleteHabit(habit)
                _eventNetworkError.value = false
            } catch (networkException: IOException) {
                _eventNetworkError.value = true
            }
        }
    }
}