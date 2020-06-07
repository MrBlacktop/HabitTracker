package com.example.data

import com.example.data.database.HabitDatabaseDao
import com.example.data.network.HabitApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(private val webToken: String) {
    @Singleton
    @Provides
    fun providesHabitRepositoryImpl(
        habitDatabaseDao: HabitDatabaseDao,
        habitApiService: HabitApiService
    ) = HabitRepositoryImpl(habitDatabaseDao, habitApiService, webToken)
}