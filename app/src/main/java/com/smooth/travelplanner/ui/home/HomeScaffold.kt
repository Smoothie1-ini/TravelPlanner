package com.smooth.travelplanner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smooth.travelplanner.R

enum class CraneScreen {
    Eat, Shit, Fuck, Die
}

@Composable
fun TopBar(
    openDrawer: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Welcome, user.",
                color = MaterialTheme.colors.background
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .clickable(onClick = openDrawer)
                    .padding(start = 10.dp),
                tint = MaterialTheme.colors.background
            )
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun BottomBar(

) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        )
    ) {
        BottomMenu(
            items = listOf(
                BottomMenuContent("Eat", R.drawable.ic_home),
                BottomMenuContent("Sleep", R.drawable.ic_music),
                BottomMenuContent("Fuck", R.drawable.ic_moon),
                BottomMenuContent("Die", R.drawable.ic_profile)
            )
        )
    }
}

@Composable
fun Drawer() {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
//        repeat(5) { item ->
//            Text(
//                text = "Item number $item",
//                modifier = Modifier.padding(8.dp),
//                color = Color.Black
//            )
//        }
    }
}

