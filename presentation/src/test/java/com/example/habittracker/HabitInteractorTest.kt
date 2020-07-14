package com.example.habittracker

import com.example.domain.Habit
import com.example.domain.HabitInteractor
import com.example.domain.HabitRepository
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.*


class HabitInteractorTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule()


    @Test
    fun habitIsComplete() {
        coroutineRule.testDispatcher.runBlockingTest {
            val repMock = mock(HabitRepository::class.java)
            val habitInteractor = HabitInteractor(repMock)
            val habit = Habit(
                date = (Date().time / 1000).toInt(),
                count = 5,
                frequency = 10
            )

            repeat(5){
                habitInteractor.completeHabit(habit)
            }

            assertEquals(5,habit.habitDoneCount)
            assertEquals(true,habit.isComplete)
            verify(repMock, times(5)).updateHabitInDb(habit)
            verify(repMock, times(1)).completeHabit(habit)
        }
    }
}