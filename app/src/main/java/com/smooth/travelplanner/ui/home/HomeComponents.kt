package com.smooth.travelplanner.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class BottomMenuContent(
    val title: String,
    @DrawableRes val iconId: Int
)

@Composable
fun BottomMenu(
    modifier: Modifier = Modifier,
    items: List<BottomMenuContent>,
    activeHighlightColor: Color = MaterialTheme.colors.secondary,
    activeTextColor: Color = MaterialTheme.colors.background,
    inactiveTextColor: Color = Color.LightGray,
    initialSelectedItemIndex: Int = 0
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                modifier =
                when (index) {
                    1 -> {
                        Modifier.padding(start = 0.dp, top = 0.dp, end = 40.dp, bottom = 0.dp)
                    }
                    2 -> {
                        Modifier.padding(start = 40.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
                    }
                    else -> {
                        Modifier
                    }
                },
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                selectedItemIndex = index
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    modifier: Modifier = Modifier,
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = MaterialTheme.colors.secondary,
    activeTextColor: Color = MaterialTheme.colors.onSecondary,
    inactiveTextColor: Color = Color.LightGray,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight(0.75f)
            .clickable {
                onItemClick()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) activeHighlightColor else Color.Transparent
                )
                .fillMaxHeight()
                .padding(horizontal = 18.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}