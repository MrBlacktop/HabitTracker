package com.example.habittracker.habit

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.Habit
import com.example.habittracker.MainActivity

import com.example.habittracker.R
import kotlinx.android.synthetic.main.habit_fragment.*

class HabitFragment : Fragment() {

    companion object {
        private const val HABIT_INDEX = "HABIT_INDEX"
        fun createBundleWithIndex(habitIndex: Int): Bundle {
            return bundleOf(HABIT_INDEX to habitIndex)
        }
    }

    private lateinit var viewModel: HabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(HabitViewModel::class.java)

        return inflater.inflate(R.layout.habit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            ArrayAdapter.createFromResource(
                it, R.array.priority_array,
                R.layout.support_simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                prioritySpinner.adapter = adapter
            }
        }

        if (arguments!!.containsKey(HABIT_INDEX)) {
            val index = arguments!!.getInt(HABIT_INDEX)
            val habit = MainActivity.habits[index]
            fillHabit(habit)
            save(habit)
        }
        else
            save()
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
            val checkedRadioButton = view?.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            habit.type = checkedRadioButton?.text.toString()
            habit.times = timesField.text.toString()
            habit.periodicity = periodicityField.text.toString()
            this.findNavController().navigate(R.id.action_habitFragment_to_habitListFragment)
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
