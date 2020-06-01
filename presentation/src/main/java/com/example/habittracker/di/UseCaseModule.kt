package com.example.habittracker.di

import com.example.data.HabitRepositoryImpl
import com.example.domain.HabitRepository
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun providesHabitRepository(habitRepositoryImpl: HabitRepositoryImpl): HabitRepository {
        return habitRepositoryImpl
    }
}