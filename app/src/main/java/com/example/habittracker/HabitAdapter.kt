package com.example.habittracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitAdapter(private val clickListener:HabitListener) : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {
    var data = listOf<Habit>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position, clickListener)


    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val habitName: TextView = itemView.findViewById(R.id.habitName)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val habitType: TextView = itemView.findViewById(R.id.habitType)
        private val priority: TextView = itemView.findViewById(R.id.priority)
        private val times: TextView = itemView.findViewById(R.id.times)
        private val periodicity: TextView = itemView.findViewById(R.id.periodicity)

        fun bind(
            item: Habit,
            position: Int,
            clickListener: HabitListener
        ) {
            habitName.text = item.name
            description.text = item.description
            habitType.text = item.type
            priority.text = item.priority

            if(item.times != "")
                times.text = item.times

            if(item.periodicity != "")
                periodicity.text = item.periodicity

            itemView.setOnClickListener {
                clickListener.onClick(position)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_habit, parent, false)
                return ViewHolder(view)
            }
        }


    }

    class HabitListener(val clickListener: (habitId: Int) ->  Unit ){
        fun onClick(habitPosition: Int) = clickListener(habitPosition)
    }
}