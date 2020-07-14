package com.example.habittracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.Habit
import com.example.domain.HabitInteractor
import com.example.habittracker.habitEditor.HabitEditorViewModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class HabitEditorViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun newHabitIsCreated(){
        val interactorMock = mock(HabitInteractor::class.java)
        val viewModel = HabitEditorViewModel(interactorMock)

        val currentHabit = viewModel.currentHabit

        assertEquals("",currentHabit.uid)
    }

    @Test
    fun afterDeleteButtonNavigationHappens(){

        Dispatchers.setMain(testDispatcher)

        val interactorMock = mock(HabitInteractor::class.java)
        val habit = Habit("a")
        whenever(interactorMock.getHabit("a")).thenReturn(habit)

        val viewModel = HabitEditorViewModel(interactorMock,"a")

        viewModel.deleteButtonClicked()

        assertEquals(true,viewModel.navigateToHabitList.value)

        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}