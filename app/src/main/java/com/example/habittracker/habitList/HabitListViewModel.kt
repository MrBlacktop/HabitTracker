package com.example.habittracker.habitList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.Habit

class HabitListViewModel : ViewModel() {
     var habits: MutableLiveData<List<Habit>> = MutableLiveData()
}
