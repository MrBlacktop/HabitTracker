package com.example.habittracker.habitList.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.database.Habit
import com.example.habittracker.databinding.ListItemHabitBinding

class HabitAdapter(private val clickListener: HabitListener) :
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
        holder.bind(item, position, clickListener)


    }

    class ViewHolder private constructor(val binding: ListItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Habit,
            position: Int,
            clickListener: HabitListener
        ) {
            binding.habit = item
            itemView.setOnClickListener {
                clickListener.onClick(item.id)
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

    class HabitListener(val clickListener: (habitId: Long) -> Unit) {
        fun onClick(habitPosition: Long) = clickListener(habitPosition)
    }
}