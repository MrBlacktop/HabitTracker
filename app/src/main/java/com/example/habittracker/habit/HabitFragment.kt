package com.example.habittracker.habit

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.database.Habit
import com.example.habittracker.MainActivity

import com.example.habittracker.R
import com.example.habittracker.database.HabitDatabase
import com.example.habittracker.database.HabitType
import kotlinx.android.synthetic.main.habit_fragment.*

class HabitFragment : Fragment() {

    companion object {
        private const val HABIT_INDEX = "HABIT_INDEX"
        fun createBundleWithIndex(habitIndex: Long): Bundle {
            return bundleOf(HABIT_INDEX to habitIndex)
        }
    }

    private lateinit var viewModel: HabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource =
            HabitDatabase.getInstance(application).habitDatabaseDao


        val args = requireArguments()
        var id: Long?

        id = if(args.containsKey(HABIT_INDEX))
            args.getLong(HABIT_INDEX)
        else
            null

        val viewModelFactory = HabitViewModelFactory(dataSource,id)
        viewModel = ViewModelProvider(this,viewModelFactory).get(HabitViewModel::class.java)

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

        viewModel.navigateToHabitList.observe(viewLifecycleOwner, Observer {
            if(it == true){
                this.findNavController().navigate(R.id.action_habitFragment_to_habitListFragment)
                viewModel.doneNavigating()
            }
        })

        if(arguments != null)
            fillHabit()
        save()
    }


    private fun save() {
        saveButton.setOnClickListener {
            val habit = viewModel.currentHabit

            habit.name = nameField.text.toString()
            habit.description = descriptionField.text.toString()
            habit.priority = prioritySpinner.selectedItem.toString()
            val checkedRadioButton =
                view?.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            habit.type = HabitType.valueOf(checkedRadioButton?.text.toString())
            habit.times = timesField.text.toString()
            habit.periodicity = periodicityField.text.toString()
            viewModel.saveButtonClicked()
        }
    }

    private fun fillHabit() {
        val habit = viewModel.currentHabit

        nameField.setText(habit.name)
        descriptionField.setText(habit.description)
        prioritySpinner.setSelection(
            resources
                .getStringArray(R.array.priority_array)
                .indexOf(habit.priority)
        )
        when (habit.type) {
            HabitType.Good -> radioGroup.check(R.id.first)
            HabitType.Bad -> radioGroup.check(R.id.second)
        }
        timesField.setText(habit.times)
        periodicityField.setText(habit.periodicity)


    }
}
