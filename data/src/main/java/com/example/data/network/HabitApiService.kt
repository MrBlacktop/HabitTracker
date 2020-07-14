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
    fun deleteHabitAsync(@Header("Authorization") token: String, @Body uid: Uid): Deferred<Respond?>

    @POST("habit_done")
    fun completeHabitAsync(@Header("Authorization") token: String, @Body habitDone: HabitDone): Deferred<Respond?>
}
