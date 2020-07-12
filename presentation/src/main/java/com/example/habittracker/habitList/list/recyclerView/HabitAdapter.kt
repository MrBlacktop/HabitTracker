package com.example.habittracker.habitList.list.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Habit
import com.example.habittracker.databinding.ListItemHabitBinding

class HabitAdapter(
    private val clickListener: HabitListener,
    private val habitCompleteListener: HabitCompleteListener
) :
    RecyclerView.Adapter<HabitAdapter.ViewHolder>() {
    var data = listOf<Habit>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener, habitCompleteListener)


    }

    class ViewHolder private constructor(val binding: ListItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Habit,
            clickListener: HabitListener,
            habitCompleteListener: HabitCompleteListener
        ) {
            binding.habit = item

            binding.root.setOnClickListener {
                clickListener.onClick(item.uid)
            }

            binding.completionButton.setOnClickListener {
                habitCompleteListener.onClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHabitBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(
                    binding
                )
            }
        }
    }

    class HabitListener(val clickListener: (habitId: String) -> Unit) {
        fun onClick(habitPosition: String) = clickListener(habitPosition)
    }

    class HabitCompleteListener(val clickListener: (habit: Habit) -> Unit) {
        fun onClick(habit: Habit) = clickListener(habit)
    }
}