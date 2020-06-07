package com.example.habittracker.habitList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.habit_list_fragment.*

class HabitListFragment : Fragment() {

    companion object {
        fun newInstance() = HabitListFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.habit_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        activity?.let { activity ->
            habitViewPager.adapter = ViewPagerAdapter(activity as AppCompatActivity)
            TabLayoutMediator(tab_layout, habitViewPager) { tab, position ->
                when(position){
                    0 -> tab.text = "Good"
                    else -> tab.text = "Bad"
                }
            }.attach()

            fab.setOnClickListener() {
                this.findNavController()
                    .navigate(R.id.action_habitListFragment_to_habitFragment)
            }
        }
    }
}
