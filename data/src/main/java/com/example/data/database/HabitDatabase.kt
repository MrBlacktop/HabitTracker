package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HabitDbModel::class], version = 3)
abstract class HabitDatabase : RoomDatabase() {
    abstract val habitDatabaseDao: HabitDatabaseDao
}