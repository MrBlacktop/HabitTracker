package com.example.habittracker.habitList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.MainActivity

import com.example.habittracker.R
import com.example.habittracker.habit.HabitFragment
import kotlinx.android.synthetic.main.fragment_list.*

private const val ARG_PARAM = "habitType"


class ListFragment : Fragment() {
    private var habitsType: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habitsType = it.getString(ARG_PARAM)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HabitAdapter(HabitAdapter.HabitListener {
            this.findNavController().navigate(
                R.id.action_habitListFragment_to_habitFragment,
                HabitFragment.createBundleWithIndex(it)
            )
        })
        habitList.adapter = adapter
        habitList.layoutManager = LinearLayoutManager(context)
        adapter.data = MainActivity.habits.filter { habit -> habit.type == habitsType }
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
