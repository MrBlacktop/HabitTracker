package com.example.habittracker

import android.app.Application
import com.example.data.RepositoryModule
import com.example.data.database.DatabaseModule
import com.example.habittracker.di.AppComponent
import com.example.habittracker.di.DaggerAppComponent

class HabitTrackerApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()


        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .repositoryModule(RepositoryModule(getString(R.string.webToken)))
            .build()
    }
}