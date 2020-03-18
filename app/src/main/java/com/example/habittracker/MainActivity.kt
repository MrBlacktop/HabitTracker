package com.example.habittracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = HabitActivity.getIntent(this)
            startActivity(intent)
        }

        adapter = HabitAdapter(HabitAdapter.HabitListener {
            val intent = HabitActivity.getIntentWithExtra(it, this)
            startActivity(intent)
        })
        habitList.layoutManager = LinearLayoutManager(this)
        habitList.adapter = adapter


        adapter.data = habits
    }

    override fun onResume() {
        super.onResume()
        adapter.data = habits
    }

    companion object{
        val habits = mutableListOf<Habit>()
    }
}
