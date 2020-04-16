package com.example.habittracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.habittracker.database.HabitDatabase
import com.example.habittracker.habitList.list.ListViewModel
import com.example.habittracker.habitList.list.ListViewModelFactory
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*


class BottomSheetFragment : Fragment() {
private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = HabitDatabase.getInstance(application).habitDatabaseDao
        val viewModelFactory = ListViewModelFactory(dataSource)
        viewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchFilterField.doOnTextChanged { text, start, count, after ->
            viewModel.textSort.value = text.toString()
        }

        decreaseSortButton.setOnClickListener{
            viewModel.increaseSort.value = false
        }

        increaseSortButton.setOnClickListener{
            viewModel.increaseSort.value = true
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BottomSheetFragment()
    }
}
