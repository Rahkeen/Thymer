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

private const val DISPLAY_WIDTH = 3
private const val DISPLAY_HEIGHT = 5

data class TimerState(
    val timerValue: Int = 0,
) : MavericksState {
    private val numDigits = numDigits(timerValue)
    private val numSpaces = numDigits - 1
    val timerDisplay = mergeDisplays(valueToDisplays())
    val numColumns = DISPLAY_WIDTH * numDigits + numSpaces

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
        for (row in 0 until DISPLAY_HEIGHT) {
            val mergedRow = mutableListOf<Int>()
            displays.forEachIndexed { idx, display ->
                mergedRow += if (idx == 0) {
                    display.numbers[row]
                } else {
                    mutableListOf(0) + display.numbers[row]
                }
            }
            mergedNumbers.add(mergedRow)
        }
        return TimerDisplay(mergedNumbers)
    }
}

data class TimerDisplay(
    val numbers: List<List<Int>>
)

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

sealed class TimerAction {
    object Start: TimerAction()
    data class SetTime(val time: Int): TimerAction()
}

class TimerViewModel(initialState: TimerState) : MavericksViewModel<TimerState>(initialState) {
    private var startTime = 20

    fun send(action: TimerAction) {
        when(action) {
            is TimerAction.SetTime -> {
                updateTime(action.time)
            }
            is TimerAction.Start -> {
               start()
            }
        }
    }

    private fun start() {
        viewModelScope.launch {
            for (i in startTime downTo 0) {
                setState {
                    copy(timerValue = i)
                }
                delay(1000L)
            }
        }
    }

    private fun updateTime(time: Int) {
        setState {
            copy(timerValue = time)
        }
    }
}
