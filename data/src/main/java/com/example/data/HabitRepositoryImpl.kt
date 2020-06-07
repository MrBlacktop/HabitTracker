package com.example.data

import com.example.data.database.*
import com.example.data.network.HabitApiService
import com.example.data.network.asDatabaseModel
import com.example.domain.Habit
import com.example.domain.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepositoryImpl(
    private val localDatabase: HabitDatabaseDao,
    private val habitApi: HabitApiService,
    private val webToken: String
) : HabitRepository {
    override fun getHabits(): Flow<List<Habit>> {
        return localDatabase.getAllHabits().map { it.map { entity -> entity.asDomainModel() } }
    }

    override suspend fun synchronize() {
        withContext(Dispatchers.IO) {
            var habits = habitApi.getHabitsAsync(webToken).await()
            localDatabase.insertAll(habits.asDatabaseModel())
        }
    }

    override suspend fun addHabit(habit: Habit) {
        withContext(Dispatchers.IO) {
            val habitDb = habit.asDbModel()
            var habitUid = habitApi.addHabitAsync(webToken, habitDb.asWebModel()).await()
            habit.uid = habitUid.uid
            localDatabase.insert(habitDb)
        }
    }

    override suspend fun updateHabit(habit: Habit) {
        withContext(Dispatchers.IO) {
            val habitDb = habit.asDbModel()
            habitApi.updateHabitAsync(webToken, habitDb.asWebModel())
            localDatabase.update(habitDb)
        }
    }

    override suspend fun deleteHabit(habit: Habit) {
        withContext(Dispatchers.IO) {
            habitApi.deleteHabit(webToken, habit.uid)
            localDatabase.delete(habit.asDbModel())
        }
    }

    override fun getHabit(uid: String): Habit {
        return localDatabase.get(uid).asDomainModel()
    }

}