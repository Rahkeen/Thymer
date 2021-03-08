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

val ONE = TimerDisplay(
    listOf(
        listOf(1, 1, 0),
        listOf(0, 1, 0),
        listOf(0, 1, 0),
        listOf(0, 1, 0),
        listOf(1, 1, 1),
    )
)

val TWO = TimerDisplay(
    listOf(
        listOf(1, 1, 1),
        listOf(0, 0, 1),
        listOf(1, 1, 1),
        listOf(1, 0, 0),
        listOf(1, 1, 1),
    )
)

val THREE = TimerDisplay(
    listOf(
        listOf(1, 1, 1),
        listOf(0, 0, 1),
        listOf(1, 1, 1),
        listOf(0, 0, 1),
        listOf(1, 1, 1),
    )
)

val FOUR = TimerDisplay(
    listOf(
        listOf(1, 0, 1),
        listOf(1, 0, 1),
        listOf(1, 1, 1),
        listOf(0, 0, 1),
        listOf(0, 0, 1),
    )
)

val FIVE = TimerDisplay(
    listOf(
        listOf(1, 1, 1),
        listOf(1, 0, 0),
        listOf(1, 1, 1),
        listOf(0, 0, 1),
        listOf(1, 1, 1),
    )
)

val SIX = TimerDisplay(
    listOf(
        listOf(1, 1, 1),
        listOf(1, 0, 0),
        listOf(1, 1, 1),
        listOf(1, 0, 1),
        listOf(1, 1, 1),
    )
)

val SEVEN = TimerDisplay(
    listOf(
        listOf(1, 1, 1),
        listOf(0, 0, 1),
        listOf(0, 0, 1),
        listOf(0, 0, 1),
        listOf(0, 0, 1),
    )
)

val EIGHT = TimerDisplay(
    listOf(
        listOf(1, 1, 1),
        listOf(1, 0, 1),
        listOf(1, 1, 1),
        listOf(1, 0, 1),
        listOf(1, 1, 1),
    )
)

val NINE = TimerDisplay(
    listOf(
        listOf(1, 1, 1),
        listOf(1, 0, 1),
        listOf(1, 1, 1),
        listOf(0, 0, 1),
        listOf(0, 0, 1),
    )
)

val ZERO = TimerDisplay(
    listOf(
        listOf(1, 1, 1),
        listOf(1, 0, 1),
        listOf(1, 0, 1),
        listOf(1, 0, 1),
        listOf(1, 1, 1),
    )
)

val intToDisplay = mapOf(
    1 to ONE,
    2 to TWO,
    3 to THREE,
    4 to FOUR,
    5 to FIVE,
    6 to SIX,
    7 to SEVEN,
    8 to EIGHT,
    9 to NINE,
    0 to ZERO,
)

fun numDigits(number: Int): Int {
    if (number == 0) return 1
    var current = number
    var digits = 0
    while (current > 0) {
        current /= 10
        digits++
    }
    return digits
}
