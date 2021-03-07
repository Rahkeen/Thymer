/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.data

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DISPLAY_SIZE = 5

data class TimerState(
    val timerValue: Int = 0,
) : MavericksState {
    val timerDisplay = mergeDisplays(valueToDisplays())
    val numColumns = DISPLAY_SIZE * numDigits(timerValue)

    private fun valueToDisplays(): List<TimerDisplay> {
        if (timerValue == 0) return listOf(ZERO)
        var current = timerValue
        val displays = mutableListOf<TimerDisplay>()
        while (current > 0) {
            val digit = current % 10
            displays.add(0, intToDisplay.getValue(digit))
            current /= 10
        }
        return displays
    }

    private fun mergeDisplays(displays: List<TimerDisplay>): TimerDisplay {
        val mergedNumbers = mutableListOf<List<Int>>()
        for (row in 0 until DISPLAY_SIZE) {
            val mergedRow = mutableListOf<Int>()
            displays.forEach { display ->
                mergedRow += display.numbers[row]
            }
            mergedNumbers.add(mergedRow)
        }
        return TimerDisplay(mergedNumbers)
    }
}

private fun numDigits(number: Int): Int {
    if (number == 0) return 1
    var current = number
    var digits = 0
    while (current > 0) {
        current /= 10
        digits++
    }
    return digits
}

data class TimerDisplay(
    val numbers: List<List<Int>>
)

class TimerViewModel(initialState: TimerState) : MavericksViewModel<TimerState>(initialState) {
    private val startTime = 20

    fun start() {
        viewModelScope.launch {
            for (i in startTime downTo 0) {
                setState {
                    copy(timerValue = i)
                }
                delay(1000L)
            }
        }
    }
}
