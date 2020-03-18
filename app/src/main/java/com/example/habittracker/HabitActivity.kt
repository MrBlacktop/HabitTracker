package com.example.habittracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_habit.*

class HabitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit)

        val spinner: Spinner = findViewById(R.id.prioritySpinner)
        ArrayAdapter.createFromResource(
            this, R.array.priority_array,
            R.layout.support_simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        if(intent.hasExtra(KEY)) {
            val index = intent.getIntExtra(KEY, 0)
            val habit = MainActivity.habits[index]
            fillHabit(habit)
            save(habit)
        }
        else
            save()
    }

    private fun save(existedHabit: Habit? = null){
        saveButton.setOnClickListener {
            val habit: Habit
            if(existedHabit == null) {
                habit = Habit()
                MainActivity.habits.add(habit)
            }
            else
                habit = existedHabit


            habit.name = nameField.text.toString()
            habit.description = descriptionField.text.toString()
            habit.priority = prioritySpinner.selectedItem.toString()
            val checkedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            habit.type = checkedRadioButton?.text.toString()
            habit.times = timesField.text.toString()
            habit.periodicity = periodicityField.text.toString()
            this.finish()
        }
    }

    private fun fillHabit(habit: Habit) {
        nameField.setText(habit.name)
        descriptionField.setText(habit.description)
        prioritySpinner.setSelection(resources
            .getStringArray(R.array.priority_array)
            .indexOf(habit.priority))
        when (habit.type) {
            "Good" -> radioGroup.check(R.id.first)
            "Bad" -> radioGroup.check(R.id.second)
        }
        timesField.setText(habit.times)
        periodicityField.setText(habit.periodicity)


    }

    companion object {
        const val KEY = "INDEX"
        fun getIntentWithExtra(index: Int, context: Context) =
            Intent(context, HabitActivity::class.java).apply {
                putExtra(KEY, index)
            }

        fun getIntent(context: Context) = Intent(context, HabitActivity::class.java)
    }
}
