package com.example.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>

    suspend fun synchronize()

    suspend fun addHabit(habit: Habit)

    suspend fun updateHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)

    fun getHabit(uid: String): Habit

    suspend fun completeHabit(habit: Habit)
}