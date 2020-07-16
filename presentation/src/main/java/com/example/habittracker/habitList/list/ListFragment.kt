package com.example.habittracker.habitList.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.Habit
import com.example.domain.HabitType
import com.example.habittracker.HabitTrackerApplication
import com.example.habittracker.R
import com.example.habittracker.habitEditor.HabitEditorFragment
import com.example.habittracker.habitList.list.recyclerView.HabitAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

private const val ARG_PARAM = "habitType"


class ListFragment : Fragment() {
    private var habitsType: HabitType? = null
    private lateinit var viewModel: ListViewModel

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habitsType = it.getString(ARG_PARAM)?.let { it1 -> HabitType.valueOf(it1) }
        }

        (requireActivity().application as HabitTrackerApplication).appComponent.inject(this)

        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(ListViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val habitOnClick: (habitId: String) -> Unit = {
            this.findNavController().navigate(
                R.id.action_habitListFragment_to_habitFragment,
                HabitEditorFragment.createBundleWithIndex(it)
            )
        }
        val habitCompleteButtonOnClick: (habit: Habit) -> Unit = {
            viewModel.habitCompleteButtonClicked(it)
            val toastMessage = getHabitCompleteToastMessage(it)
            Toast.makeText(context,toastMessage,Toast.LENGTH_SHORT).show()
        }

        val adapter = HabitAdapter(
            HabitAdapter.HabitListener(habitOnClick),
            HabitAdapter.HabitCompleteListener(habitCompleteButtonOnClick)
        )

        habitList.adapter = adapter
        habitList.layoutManager = LinearLayoutManager(context)

        viewModel.habits.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = viewModel.getFilteredHabits(habitsType!!)
            }
        })

        viewModel.increaseSort.observe(viewLifecycleOwner, Observer {
            adapter.data = viewModel.getFilteredHabits(habitsType!!)
        })

        viewModel.textSort.observe(viewLifecycleOwner, Observer {
            adapter.data = viewModel.getFilteredHabits(habitsType!!)
        })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer {
            if (it)
                Toast.makeText(context, getText(R.string.network_error), Toast.LENGTH_SHORT).show()
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    private fun getHabitCompleteToastMessage(habit: Habit): String {
        if (habitsType == HabitType.Bad) {
            return if (habit.isComplete)
                getString(R.string.bad_habit_complete)
            else
                getString(
                    R.string.bad_habit_is_not_yet_complete,
                    habit.count - habit.habitDoneCount
                )
        } else {
            return if (habit.isComplete)
                getString(R.string.good_habit_complete)
            else
                getString(
                    R.string.good_habit_is_not_yet_complete,
                    habit.count - habit.habitDoneCount
                )
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(habitType: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, habitType)
                }
            }
    }
}
