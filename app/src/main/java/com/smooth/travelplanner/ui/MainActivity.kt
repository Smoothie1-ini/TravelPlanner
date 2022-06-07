@file:OptIn(ExperimentalAnimationApi::class)

package com.smooth.travelplanner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.smooth.travelplanner.ui.theme.TravelPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelPlannerTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}