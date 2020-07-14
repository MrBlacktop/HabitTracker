package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.data.network.HabitWebModel
import com.example.domain.Habit
import com.example.domain.HabitType
import com.example.domain.Priority


@Entity
@TypeConverters(HabitTypeConverter::class, HabitPriorityConverter::class)
data class HabitDbModel(
    @PrimaryKey
    var uid: String = "",
    var title: String = "",
    var description: String = "",
    var priority: Priority = Priority.High,
    var type: HabitType = HabitType.Good,
    var count: Int = 0,
    var frequency: Int = 0,
    var color: Int = 0,
    var date: Int = 0,
    var habitDoneCount: Int = 0,
    var isComplete: Boolean = false
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

fun HabitDbModel.asWebModel(): HabitWebModel {
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
        priority = this.priority.ordinal,
        title = this.title,
        type = this.type.ordinal,
        uid = uid
    )
}

fun HabitDbModel.asDomainModel(): Habit {
    return Habit(
        color = this.color,
        count = this.count,
        date = this.date,
        description = this.description,
        frequency = this.frequency,
        priority = this.priority,
        title = this.title,
        type = this.type,
        uid = this.uid,
        habitDoneCount = this.habitDoneCount,
        isComplete = this.isComplete
    )
}

fun List<HabitDbModel>.asDomainModel(): List<Habit> {
    return map { it.asDomainModel() }
}

fun Habit.asDbModel(): HabitDbModel {
    return HabitDbModel(
        uid = this.uid,
        title = this.title,
        description = this.description,
        priority = this.priority,
        type = this.type,
        count = this.count,
        frequency = this.frequency,
        color = this.color,
        date = this.date,
        habitDoneCount = this.habitDoneCount,
        isComplete = this.isComplete
    )
}
