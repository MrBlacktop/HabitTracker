package com.example.habittracker.network

import com.example.habittracker.database.Habit
import com.example.habittracker.database.HabitType
import com.example.habittracker.database.Priority
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

fun List<HabitWebModel>.asDatabaseModel(): List<Habit>{
    return map {
        Habit(
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