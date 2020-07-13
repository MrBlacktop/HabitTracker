package com.example.habittracker.habitEditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.domain.HabitInteractor
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.R
import com.example.habittracker.databinding.HabitEditorFragmentBinding
import kotlinx.android.synthetic.main.habit_editor_fragment.*
import javax.inject.Inject

class HabitEditorFragment : Fragment() {

    companion object {
        private const val HABIT_INDEX = "HABIT_INDEX"
        fun createBundleWithIndex(habitIndex: String): Bundle {
            return bundleOf(HABIT_INDEX to habitIndex)
        }
    }

    @Inject
    lateinit var habitInteractor: HabitInteractor

    private lateinit var viewModel: HabitEditorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = HabitEditorFragmentBinding.inflate(inflater, container, false)
        (requireActivity().application as HabitTrackerApplication).appComponent.inject(this)


        val args = requireArguments()
        val uid: String?

        if (args.containsKey(HABIT_INDEX)) {
            uid = args.getString(HABIT_INDEX)
            binding.deleteButton.visibility = View.VISIBLE
        } else {
            uid = null
            binding.deleteButton.visibility = View.GONE
        }

        val viewModelFactory = HabitEditorViewModelFactory(habitInteractor, uid)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HabitEditorViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
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

    }
}
