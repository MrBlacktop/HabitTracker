package com.example.habittracker

data class Habit(
    var name: String = "",
    var description: String = "",
    var priority : String = "",
    var type : String = "",
    var times: String = "",
    var periodicity: String = ""
    )