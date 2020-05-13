package com.example.habittracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.habittracker.network.HabitWebModel
import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.JsonClass

@Entity
@TypeConverters(HabitTypeConverter::class, HabitPriorityConverter::class)
data class Habit(
    @PrimaryKey
    var uid: String ="",
    var title: String = "",
    var description: String = "",
    var priority: Priority = Priority.High,
    var type: HabitType = HabitType.Good,
    var count: Int = 0,
    var frequency: Int = 0,
    var color: Int = 0,
    var date: Int = 0
)

class HabitTypeConverter {
    @TypeConverter
    fun fromEnum(enum: HabitType): Int {
        return enum.ordinal
    }

    @TypeConverter
    fun toEnum(data: Int): HabitType {
        return HabitType.values()[data]
    }
}

class HabitPriorityConverter {
    @TypeConverter
    fun fromEnum(enum: Priority): Int {
        return enum.ordinal
    }

    @TypeConverter
    fun toEnum(data: Int): Priority {
        return Priority.values()[data]
    }
}

fun Habit.asWebModel(): HabitWebModel {
    val uid = if (this.uid == "")
        null
    else
        this.uid
    return HabitWebModel(
        color = this.color,
        count = this.count,
        date = this.date,
        description = this.description,
        frequency = this.frequency,
        priority = this.priority.value,
        title = this.title,
        type = this.type.value,
        uid = uid
    )
}


