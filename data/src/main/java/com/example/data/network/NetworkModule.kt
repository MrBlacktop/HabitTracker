package com.example.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule{
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit
    }

    @Singleton
    @Provides
    fun providesHabitApi(retrofit: Retrofit): HabitApiService{
        return retrofit.create(HabitApiService::class.java)
    }
}