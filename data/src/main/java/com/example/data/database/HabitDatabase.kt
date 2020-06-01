package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HabitDbModel::class], version = 1)
abstract class HabitDatabase : RoomDatabase() {
    abstract val habitDatabaseDao: HabitDatabaseDao

//    companion object {
//        @Volatile
//        private var INSTANCE: HabitDatabase? = null
//
//        fun getInstance(context: Context): HabitDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        HabitDatabase::class.java,
//                        "habit_database"
//                    )
//                        .allowMainThreadQueries()
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
}