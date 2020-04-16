package com.example.habittracker.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface HabitDatabaseDao {
    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Query("SELECT * FROM habit WHERE id = :key")
    fun get(key: Long):Habit

    @Query("SELECT * FROM habit ORDER BY id")
    fun getAllHabits() : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE :filter + '%'")
    fun getHabitsFilteredByName(filter: String): LiveData<List<Habit>>

}