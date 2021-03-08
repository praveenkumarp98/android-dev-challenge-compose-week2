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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainActivity : AppCompatActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(flow = countdownFlow())
            }
        }
    }
}

// Start building your app here!
@ExperimentalAnimationApi
@Composable
fun MyApp(flow: Flow<Int>) {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val countdownValue by flow.collectAsState(initial = 10)
            when (countdownValue) {
                in 1..10 -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Countdown", style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Light
                        ))
                        val visible by remember { mutableStateOf(true)}
                        AnimatedVisibility(
                            visible = visible,
                            enter = fadeIn(initialAlpha = 0.4f),
                            exit = fadeOut(animationSpec = tween(durationMillis = 250))
                        ) {
                            Text(
                                text = "$countdownValue",
                                style = TextStyle(
                                    fontSize = 50.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
                else -> {
                    val visible by remember { mutableStateOf(true)}
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(initialAlpha = 0.4f),
                        exit = fadeOut(animationSpec = tween(durationMillis = 250))
                    ) {
                        Text(
                            text = "Let's compose!!!",
                            style = TextStyle(
                                fontSize = 40.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

    }
}

fun countdownFlow() = flow {
    for (i in 9 downTo 0) {
        delay(1000L)
        emit(i)
    }
}

@ExperimentalAnimationApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(flow = countdownFlow())
    }
}

@ExperimentalAnimationApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp(flow = countdownFlow())
    }
}
