package com.smooth.travelplanner.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.home.trip.TripItem

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
                    if (isSelected) MaterialTheme.colors.primaryVariant else Color.Transparent
                )
                .fillMaxHeight()
                .padding(horizontal = 18.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.surface,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun TopBar() {
    Row(
        Modifier
            .padding(16.dp)
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        MyTextField(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {

            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "",
                tint = Color.White
            )
        }
        IconButton(
            onClick = {

            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notifications),
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MyTextField(
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val input = remember {
        mutableStateOf("")
    }

    TextField(
        value = input.value,
        label = {
            Text(
                text = "Search for keywords",
                fontSize = 12.sp,
                color = MaterialTheme.colors.primary
            )
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search"
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxHeight(),
        textStyle = TextStyle(
            fontSize = 14.sp
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
        onValueChange = {
            input.value = it
        }
    )
}

@Composable
fun Header() {
    Card(
        Modifier
            .height(64.dp)
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 20.dp)
            .fillMaxWidth(0.9f)
            .zIndex(2f),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
    }
}

@Composable
fun Content(

) {
    LazyColumn {
        items(10) {
            TripItem(
                modifier = if (it == 10 - 1) Modifier.padding(bottom = 55.dp) else Modifier
            )
        }
    }
}