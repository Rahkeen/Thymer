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
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.androiddevchallenge.data.TimerAction
import com.example.androiddevchallenge.data.TimerViewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.darkGreen
import com.example.androiddevchallenge.ui.theme.darkerGreen
import com.example.androiddevchallenge.ui.theme.lightGreen
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

val BOX_SIZE = 15.dp

@Composable
fun MyApp() {
    val viewModel: TimerViewModel = mavericksViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkGreen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Grid()
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            BoxButton(text = "30", action = { viewModel.send(TimerAction.SetTime(30)) })
            BoxButton(text = "60", action = { viewModel.send(TimerAction.SetTime(60)) })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            BoxButton(
                text = "Reset",
                color = darkerGreen,
                textColor = Color.White,
                action = { viewModel.send(TimerAction.Reset) }
            )
        }
    }
}

@Composable
fun BoxButton(
    text: String,
    color: Color = lightGreen,
    textColor: Color = Color.Black,
    action: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(60.dp)
            .background(color = color)
            .clickable(onClick = action),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun Grid() {
    val timerViewModel: TimerViewModel = mavericksViewModel()
    val state = timerViewModel.collectAsState()
    Column(
        modifier = Modifier
            .wrapContentSize()
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var itemId = 0
        for (row in state.value.timerDisplay.numbers.indices) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .animateContentSize(animationSpec = tween(durationMillis = 500)),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (item in 0 until state.value.numColumns) {
                    Tile(itemId, state.value.timerDisplay.numbers[row][item], row)
                    itemId++
                }
            }
        }
    }
}

@Composable
fun Tile(index: Int = 0, on: Int = 0, rowIndex: Int = 0) {
    val scope = rememberCoroutineScope()
    val tileState = remember(index) { mutableStateOf(0) }
    val transition = updateTransition(targetState = tileState.value)
    val color = transition.animateColor(transitionSpec = { tween(durationMillis = 500) }) { state ->
        when (state) {
            0 -> darkGreen
            else -> lightGreen
        }
    }
    val rotation = transition.animateFloat(transitionSpec = { tween(durationMillis = 500) }) { state ->
        when (state) {
            0 -> 0F
            else -> 180F
        }
    }

    scope.launch {
        delay(50L * rowIndex)
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
