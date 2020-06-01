package com.example.domain

data class Habit(
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
