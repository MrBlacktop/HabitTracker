package com.example.data.network

import com.example.data.database.HabitDbModel
import com.example.domain.HabitType
import com.example.domain.Priority
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HabitWebModel(
    var color: Int,
    var count: Int,
    var date: Int,
    var description: String,
    var frequency: Int,
    var priority: Int,
    var title: String,
    var type: Int,
    var uid: String?
)

fun List<HabitWebModel>.asDatabaseModel(): List<HabitDbModel>{
    return map {
        HabitDbModel(
            uid = it.uid ?: "",
            title = it.title,
            description = it.description,
            priority = Priority.values()[it.priority],
            type = HabitType.values()[it.type],
            count = it.count,
            frequency = it.frequency,
            color = it.color,
            date = it.date
        )
    }
}

@JsonClass(generateAdapter = true)
data class Uid(
    var uid: String
)

@JsonClass(generateAdapter = true)
data class HabitDone(
    var date:Int,
    @Json(name = "habit_uid")
    var habitUid: String
)

@JsonClass(generateAdapter = true)
data class Respond(
    var code: Int,
    var message: String
)