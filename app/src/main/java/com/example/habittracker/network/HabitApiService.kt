package com.example.habittracker.network

import com.example.habittracker.database.Habit
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()



interface HabitApiService {
    @GET("habit")
    fun getHabitsAsync(@Header("Authorization") token: String): Deferred<List<HabitWebModel>>

    @PUT("habit")
    fun addHabitAsync(@Header("Authorization") token: String, @Body habit:HabitWebModel): Deferred<Uid>

    @PUT("habit")
    fun updateHabitAsync(@Header("Authorization") token: String, @Body habit:HabitWebModel): Deferred<Uid>

    @HTTP(method = "DELETE",path = "habit", hasBody = true)
    fun deleteHabit(@Header("Authorization") token: String, @Body uid: String)
}

object HabitApi {
    val retrofitService: HabitApiService by lazy { retrofit.create(HabitApiService::class.java) }
}
