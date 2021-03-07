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
package com.example.androiddevchallenge.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.androiddevchallenge.data.TimerViewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.tileColor
import com.example.androiddevchallenge.ui.theme.tileSelectedColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

val BOX_SIZE = 25.dp

@Composable
fun MyApp() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Grid()
    }
}

@Composable
fun Grid() {
    val timerViewModel: TimerViewModel = mavericksViewModel()
    val state = timerViewModel.collectAsState()
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                timerViewModel.start()
            },
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var items = state.value.numCells
        while (items > 0) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (i in 1..state.value.numColumns) {
                    if (items == 0) break
                    val index = state.value.numCells - items
                    Tile(index, state.value.timerDisplay[index])
                    items--
                }
            }
        }
    }
}

@Composable
fun Tile(index: Int = 0, on: Int = 0) {
    val scope = rememberCoroutineScope()
    val tileState = remember(index) { mutableStateOf(0) }
    val transition = updateTransition(targetState = tileState.value)
    val color = transition.animateColor() { state ->
        when (state) {
            0 -> tileColor
            else -> tileSelectedColor
        }
    }
    val rotation = transition.animateFloat() { state ->
        when (state) {
            0 -> 0F
            else -> 180F
        }
    }

    scope.launch {
        delay(10L * index)
        tileState.value = on
    }

    Box(
        modifier = Modifier
            .width(BOX_SIZE)
            .aspectRatio(1F)
            .graphicsLayer(
                rotationY = rotation.value
            )
            .drawBehind {
                drawRect(color = color.value)
            }
    )
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
