package com.example.habittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.habitList.list.ListViewModel
import com.example.habittracker.habitList.list.ListViewModelFactory
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import javax.inject.Inject


class BottomSheetFragment : Fragment() {
private lateinit var viewModel: ListViewModel

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as HabitTrackerApplication).appComponent.inject(this)

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
