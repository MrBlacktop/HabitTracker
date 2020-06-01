package com.example.data.network

import kotlinx.coroutines.Deferred
import retrofit2.http.*


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
