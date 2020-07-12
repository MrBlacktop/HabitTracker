package com.example.domain

import java.util.*
import javax.inject.Inject

class HabitInteractor @Inject constructor(private val repository: HabitRepository) {
    fun getHabits() = repository.getHabits()
    suspend fun synchronize() = repository.synchronize()
    suspend fun addHabit(habit: Habit) = repository.addHabit(habit)
    suspend fun updateHabit(habit: Habit) = repository.updateHabit(habit)
    suspend fun deleteHabit(habit: Habit) = repository.deleteHabit(habit)
    fun getHabit(uid: String) = repository.getHabit(uid)

    suspend fun completeHabit(habit: Habit) {

        habit.habitDoneCount++

        if (isHabitExpire(habit)) {
            habitExpired(habit)
            return
        }

        if (habit.habitDoneCount == habit.count)
            habitComplete(habit)
    }

    private suspend fun habitExpired(habit: Habit) {
        habit.apply {
            habitDoneCount = 1
            date = (Date().time / 1000).toInt()
            isComplete = false
        }
        updateHabit(habit)
    }

    private suspend fun habitComplete(habit: Habit) {
        habit.isComplete = true
        repository.completeHabit(habit)
    }

    private fun isHabitExpire(habit: Habit): Boolean {
        val gregorianCalendar = GregorianCalendar()
        val date = Date(habit.date.toLong() * 1000)
        gregorianCalendar.time = date
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, habit.frequency)
        return Date().after(gregorianCalendar.time)
    }
}