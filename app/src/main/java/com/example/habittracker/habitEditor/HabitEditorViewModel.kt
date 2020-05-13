package com.example.habittracker.habitEditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.database.Habit
import com.example.habittracker.database.HabitDatabaseDao
import com.example.habittracker.network.HabitRepository
import kotlinx.coroutines.*

class HabitEditorViewModel(private val repository: HabitRepository, uid: String? = null) : ViewModel() {
    var currentHabit: Habit
        private set

    private var isNewHabit = true

    private val uiScope = CoroutineScope(Dispatchers.Main)

    init {
        if (uid != null) {
            currentHabit = repository.getHabit(uid)
            isNewHabit = false
        } else
            currentHabit = Habit()
    }

    private val _navigateToHabitList = MutableLiveData<Boolean?>()
    val navigateToHabitList: LiveData<Boolean?>
        get() = _navigateToHabitList


    fun saveButtonClicked() {
        uiScope.launch {
            withContext(Dispatchers.IO){
                if (isNewHabit)
                repository.addHabit(currentHabit)
                else
                repository.updateHabit(currentHabit)
            }
            _navigateToHabitList.value = true
        }
    }


    fun doneNavigating() {
        _navigateToHabitList.value = null
    }


}
