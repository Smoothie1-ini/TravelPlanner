package com.smooth.travelplanner.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.ConfigurationCompat

@Composable
fun TabBar(
    modifier: Modifier = Modifier,
    children: @Composable (Modifier) -> Unit
) {
    Row(
        modifier
    ) {
        // Separate Row as the children shouldn't have the padding
        Row(
            Modifier.padding(top = 8.dp)
        ) {
        }
        children(
            Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    titles: List<String>,
    tabSelected: CraneScreen,
    onTabSelected: (CraneScreen) -> Unit
) {
    TabRow(
        selectedTabIndex = tabSelected.ordinal,
        modifier = modifier,
        contentColor = MaterialTheme.colors.onSurface,
        indicator = { tabPositions: List<TabPosition> ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[tabSelected.ordinal])
                    .fillMaxSize()
                    .padding(0.dp)
                    .border(
                        BorderStroke(2.dp, Color.White),
                        RoundedCornerShape(16.dp)
                    )
            )
        },
        divider = { }
    ) {
        titles.forEachIndexed { index, title ->
            val selected = index == tabSelected.ordinal
            val textModifier = Modifier.padding(0.dp)

            Tab(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(vertical = 6.dp),
                selected = selected,
                onClick = {
                    onTabSelected(CraneScreen.values()[index])
                }
            ) {
                Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Tab icon")
                Text(
                    text = title.uppercase(
                        ConfigurationCompat.getLocales(LocalConfiguration.current)[0]
                    ),
                    modifier = textModifier,
                    color = MaterialTheme.colors.background,
                    fontSize = 10.sp
                )
            }
        }
    }
}