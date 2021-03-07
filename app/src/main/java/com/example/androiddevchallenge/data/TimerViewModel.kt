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

data class TimerState(
    val numCells: Int = 25,
    val numColumns: Int = 5,
    val timerValue: Int = 0,
    val timerDisplay: List<Int> = ZERO
) : MavericksState

class TimerViewModel(initialState: TimerState) : MavericksViewModel<TimerState>(initialState) {
    private val startTime = 9

    fun start() {
        viewModelScope.launch {
            for (i in startTime downTo 0) {
                setState {
                    copy(timerValue = i, timerDisplay = intToDisplay.getValue(i))
                }
                delay(1000L)
            }
        }
    }
}
