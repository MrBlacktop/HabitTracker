package com.example.habittracker.network

import androidx.lifecycle.LiveData
import com.example.habittracker.database.Habit
import com.example.habittracker.database.HabitDatabaseDao
import com.example.habittracker.database.asWebModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HabitRepository(private val localDatabase: HabitDatabaseDao, private val webToken: String) {
    private val habitApi = HabitApi.retrofitService

    fun getHabits(): LiveData<List<Habit>> {
        return localDatabase.getAllHabits()
    }

    suspend fun synchronize() {
        withContext(Dispatchers.IO) {
            var habits = habitApi.getHabitsAsync(webToken).await()
            localDatabase.insertAll(habits.asDatabaseModel())
        }
    }

    suspend fun addHabit(habit: Habit) {
        withContext(Dispatchers.IO) {
            var habitUid = habitApi.addHabitAsync(webToken, habit.asWebModel()).await()
            habit.uid = habitUid.uid
            localDatabase.insert(habit)
        }
    }

    suspend fun updateHabit(habit: Habit) {
        withContext(Dispatchers.IO) {
            habitApi.updateHabitAsync(webToken, habit.asWebModel())
            localDatabase.update(habit)
        }
    }

    suspend fun deleteHabit(habit: Habit) {
        withContext(Dispatchers.IO) {
            habitApi.deleteHabit(webToken, habit.uid)
            localDatabase.delete(habit)
        }
    }

    fun getHabit(uid: String): Habit {
        return localDatabase.get(uid)
    }
}