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

    @Query("SELECT * FROM habit WHERE uid = :uid")
    fun get(uid: String):Habit

    @Query("SELECT * FROM habit ORDER BY date")
    fun getAllHabits() : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE title LIKE :filter + '%'")
    fun getHabitsFilteredByName(filter: String): LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(habits: List<Habit>)

}