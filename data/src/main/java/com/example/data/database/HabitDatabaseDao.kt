package com.example.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDatabaseDao {
    @Insert
    fun insert(habit: HabitDbModel)

    @Update
    fun update(habit: HabitDbModel)

    @Delete
    fun delete(habit: HabitDbModel)

    @Query("SELECT * FROM habitdbmodel WHERE uid = :uid")
    fun get(uid: String):HabitDbModel

    @Query("SELECT * FROM habitdbmodel ORDER BY date")
    fun getAllHabits() : Flow<List<HabitDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(habits: List<HabitDbModel>)

}