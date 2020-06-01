package com.example.habittracker.di

import com.example.data.RepositoryModule
import com.example.data.database.DatabaseModule
import com.example.data.network.NetworkModule
import com.example.domain.HabitInteractor
import com.example.habittracker.BottomSheetFragment
import com.example.habittracker.habitEditor.HabitEditorFragment
import com.example.habittracker.habitList.HabitListFragment
import com.example.habittracker.habitList.list.ListFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class,DatabaseModule::class, UseCaseModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(habitEditorFragment: HabitEditorFragment)
    fun inject(listFragment: ListFragment)
    fun inject(bottomSheetFragment: BottomSheetFragment)
}