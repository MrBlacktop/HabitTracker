package com.example.habittracker.habitList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.R
import com.example.habittracker.databinding.HabitListFragmentBinding

class HabitListFragment : Fragment() {

    companion object {
        fun newInstance() = HabitListFragment()
    }

    private lateinit var viewModel: HabitListViewModel
    private lateinit var adapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: HabitListFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.habit_list_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(HabitListViewModel::class.java)

        binding.fab.setOnClickListener() {
            this.findNavController()
                .navigate(HabitListFragmentDirections.actionHabitListFragmentToHabitFragment())
        }


        adapter =
            HabitAdapter(HabitAdapter.HabitListener {
                findNavController().navigate(HabitListFragmentDirections.actionHabitListFragmentToHabitFragment())
            })
        binding.habitList.layoutManager = LinearLayoutManager(context)
        binding.habitList.adapter = adapter

        viewModel.habits.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }
}
