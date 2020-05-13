package com.example.habittracker.habitEditor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.habittracker.R
import com.example.habittracker.database.HabitDatabase
import com.example.habittracker.database.HabitType
import com.example.habittracker.database.Priority
import com.example.habittracker.network.HabitRepository
import kotlinx.android.synthetic.main.habit_editor_fragment.*
import java.util.*

class HabitEditorFragment : Fragment() {

    companion object {
        private const val HABIT_INDEX = "HABIT_INDEX"
        fun createBundleWithIndex(habitIndex: String): Bundle {
            return bundleOf(HABIT_INDEX to habitIndex)
        }
    }

    private lateinit var viewModel: HabitEditorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource =
            HabitDatabase.getInstance(application).habitDatabaseDao
        val repository = HabitRepository(dataSource,getString(R.string.webToken))


        val args = requireArguments()
        val uid: String?

        uid = if (args.containsKey(HABIT_INDEX))
            args.getString(HABIT_INDEX)
        else
            null

        val viewModelFactory = HabitEditorViewModelFactory(repository, uid)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HabitEditorViewModel::class.java)

        return inflater.inflate(R.layout.habit_editor_fragment, container, false)
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
            if (it == true) {
                this.findNavController().navigate(R.id.action_habitFragment_to_habitListFragment)
                viewModel.doneNavigating()
            }
        })

        if (arguments != null)
            fillHabit()
        save()
    }


    private fun save() {
        saveButton.setOnClickListener {
            val habit = viewModel.currentHabit

            habit.title = nameField.text.toString()
            habit.description = descriptionField.text.toString()
            habit.priority = Priority.valueOf(prioritySpinner.selectedItem.toString())
            val checkedRadioButton =
                view?.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            habit.type = HabitType.valueOf(checkedRadioButton?.text.toString())
            habit.count = timesField.text.toString().toInt()
            habit.frequency = periodicityField.text.toString().toInt()
            habit.date = (Date().time / 1000).toInt()
            viewModel.saveButtonClicked()
        }
    }

    private fun fillHabit() {
        val habit = viewModel.currentHabit
        Log.e("FillHabit",habit.toString())
        nameField.setText(habit.title)
        descriptionField.setText(habit.description)
        prioritySpinner.setSelection(
            resources
                .getStringArray(R.array.priority_array)
                .indexOf(habit.priority.name)
        )
        when (habit.type) {
            HabitType.Good -> radioGroup.check(R.id.first)
            HabitType.Bad -> radioGroup.check(R.id.second)
        }
        timesField.setText(habit.count.toString())
        periodicityField.setText(habit.frequency.toString())


    }
}
