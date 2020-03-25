package com.example.habittracker.habit

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.Habit
import com.example.habittracker.MainActivity

import com.example.habittracker.R
import kotlinx.android.synthetic.main.activity_habit.*

class HabitFragment : Fragment() {

    companion object {
        fun newInstance() = HabitFragment()
    }

    private lateinit var viewModel: HabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(HabitViewModel::class.java)



        return inflater.inflate(R.layout.habit_fragment, container, false)
    }


    private fun save(existedHabit: Habit? = null) {
        saveButton.setOnClickListener {
            val habit: Habit
            if (existedHabit == null) {
                habit = Habit()
                MainActivity.habits.add(habit)
            } else
                habit = existedHabit


            habit.name = nameField.text.toString()
            habit.description = descriptionField.text.toString()
            habit.priority = prioritySpinner.selectedItem.toString()
//            val checkedRadioButton = .findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
//            habit.type = checkedRadioButton?.text.toString()
            habit.times = timesField.text.toString()
            habit.periodicity = periodicityField.text.toString()
            findNavController().navigate(HabitFragmentDirections.actionHabitFragmentToHabitListFragment())
        }
    }

    private fun fillHabit(habit: Habit) {
        nameField.setText(habit.name)
        descriptionField.setText(habit.description)
        prioritySpinner.setSelection(
            resources
                .getStringArray(R.array.priority_array)
                .indexOf(habit.priority)
        )
        when (habit.type) {
            "Good" -> radioGroup.check(R.id.first)
            "Bad" -> radioGroup.check(R.id.second)
        }
        timesField.setText(habit.times)
        periodicityField.setText(habit.periodicity)


    }
}
