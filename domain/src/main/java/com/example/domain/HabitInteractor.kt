package com.example.domain

import javax.inject.Inject

class HabitInteractor @Inject constructor(private val repository: HabitRepository) {
    fun getHabits() = repository.getHabits()
    suspend fun synchronize() = repository.synchronize()
    suspend fun addHabit(habit: Habit) = repository.addHabit(habit)
    suspend fun updateHabit(habit: Habit) = repository.updateHabit(habit)
    suspend fun deleteHabit(habit: Habit) = repository.deleteHabit(habit)
    fun getHabit(uid: String) = repository.getHabit(uid)
}