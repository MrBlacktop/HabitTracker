package com.example.habittracker.habitList.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.domain.Habit
import com.example.domain.HabitInteractor
import com.example.domain.HabitType
import com.example.habittracker.R
import kotlinx.android.synthetic.main.list_item_habit.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class ListViewModel(private val habitInteractor: HabitInteractor) : ViewModel() {

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

    val habits = habitInteractor.getHabits().asLiveData()


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
                habitInteractor.synchronize()
                _eventNetworkError.value = false
            } catch (networkException: IOException) {
                _eventNetworkError.value = true
            }

        }
    }

    fun deleteHabit(habit: Habit) {
        uiScope.launch {
            try {
                habitInteractor.deleteHabit(habit)
                _eventNetworkError.value = false
            } catch (networkException: IOException) {
                _eventNetworkError.value = true
            }
        }
    }


    fun habitCompleteButtonClicked(habit: Habit){
        uiScope.launch {
            habitInteractor.completeHabit(habit)
        }
    }
}