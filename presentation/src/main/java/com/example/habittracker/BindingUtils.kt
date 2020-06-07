package com.example.habittracker

import androidx.databinding.InverseMethod
import com.example.domain.HabitType
import com.example.domain.Priority


@InverseMethod("positionToPriority")
fun priorityToPosition(priority: Priority?): Int {
    return priority?.ordinal ?: 0
}

fun positionToPriority(position: Int): Priority {
    return Priority.values()[position]
}

@InverseMethod("buttonIdToHabitType")
fun habitTypeToButtonId(habitType: HabitType?) :Int{
    return when(habitType){
        HabitType.Bad -> R.id.second
        else -> R.id.first
    }
}

fun buttonIdToHabitType(selectedButtonId: Int): HabitType{
    return when(selectedButtonId){
        R.id.first -> HabitType.Good
        else -> HabitType.Bad
    }
}
@InverseMethod("stringToInt")
fun intToSting(int: Int): String{
    return int.toString()
}

fun stringToInt(string: String):Int{
    return string.toIntOrNull() ?: 0
}


