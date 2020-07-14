package com.example.habittracker.habitEditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.Habit
import com.example.domain.HabitInteractor
import kotlinx.coroutines.*
import java.util.*

class HabitEditorViewModel(private val habitInteractor: HabitInteractor, uid: String? = null) :
    ViewModel() {
    var currentHabit: Habit
        private set

    private var isNewHabit = true

    private val uiScope = CoroutineScope(Dispatchers.Main)

    init {
        if (uid != null) {
            currentHabit = habitInteractor.getHabit(uid)
            isNewHabit = false
        } else
            currentHabit = Habit()
    }

    private val _navigateToHabitList = MutableLiveData<Boolean?>()
    val navigateToHabitList: LiveData<Boolean?>
        get() = _navigateToHabitList


    fun saveButtonClicked() {
        uiScope.launch {
            currentHabit.date = (Date().time / 1000).toInt()
            if (isNewHabit)
                habitInteractor.addHabit(currentHabit)
            else
                habitInteractor.updateHabit(currentHabit)
            _navigateToHabitList.value = true
        }
    }

    fun deleteButtonClicked() {
        uiScope.launch {
            if (!isNewHabit) {
                habitInteractor.deleteHabit(currentHabit)
                _navigateToHabitList.value = true
            }
        }
    }


    fun doneNavigating() {
        _navigateToHabitList.value = null
    }


}
