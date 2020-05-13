package com.example.habittracker.habitList.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.habittracker.R
import com.example.habittracker.database.HabitDatabase
import com.example.habittracker.database.HabitType
import com.example.habittracker.habitEditor.HabitEditorFragment
import com.example.habittracker.habitList.list.recyclerView.HabitAdapter
import kotlinx.android.synthetic.main.fragment_list.*

private const val ARG_PARAM = "habitType"


class ListFragment : Fragment() {
    private var habitsType: HabitType? = null
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habitsType = it.getString(ARG_PARAM)?.let { it1 -> HabitType.valueOf(it1) }
        }

        val application = requireNotNull(this.activity).application
        val dataSource = HabitDatabase.getInstance(application).habitDatabaseDao
        
        val viewModelFactory = ListViewModelFactory(dataSource,getString(R.string.webToken))
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(ListViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =
            HabitAdapter(
                HabitAdapter.HabitListener {
                    this.findNavController().navigate(
                        R.id.action_habitListFragment_to_habitFragment,
                        HabitEditorFragment.createBundleWithIndex(it)
                    )
                })

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
            if(it)
                Toast.makeText(context,"Network Error", Toast.LENGTH_SHORT).show()
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
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
