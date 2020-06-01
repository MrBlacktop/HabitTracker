package com.example.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context){
    @Singleton
    @Provides
    fun providesHabitDatabase():HabitDatabase = Room.databaseBuilder(
        context.applicationContext,HabitDatabase::class.java,"habit_database")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesHabitDatabaseDao(habitDatabase: HabitDatabase) = habitDatabase.habitDatabaseDao
}