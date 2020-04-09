package com.example.habittracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
@TypeConverters(EnumConverter::class)
data class Habit(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String = "",
    var description: String = "",
    var priority: String = "",
    var type: HabitType = HabitType.Good,
    var times: String = "",
    var periodicity: String = ""
)

class EnumConverter {
    @TypeConverter
    fun fromEnum(enum: HabitType): String {
        return enum.toString()
    }

    @TypeConverter
    fun toEnum(data: String): HabitType {
        return HabitType.valueOf(data)
    }
}


