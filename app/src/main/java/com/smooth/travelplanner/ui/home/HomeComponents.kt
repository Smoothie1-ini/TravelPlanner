package com.smooth.travelplanner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigateTo
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.NavGraphs
import com.smooth.travelplanner.ui.appCurrentDestinationAsState
import com.smooth.travelplanner.ui.destinations.ArchivedTripsTabDestination
import com.smooth.travelplanner.ui.destinations.Destination
import com.smooth.travelplanner.ui.destinations.WishlistTabDestination
import com.smooth.travelplanner.ui.startAppDestination

//@Composable
//fun BottomBar(
//    modifier: Modifier = Modifier,
//    @DrawableRes iconIds: List<Int>,
//    initialSelectedItemIndex: Int = 0,
//    onTabSelected: (selectedIndex: Int) -> Unit
//) {
//    var selectedIndex by remember {
//        mutableStateOf(initialSelectedItemIndex)
//    }
//    Row(
//        horizontalArrangement = Arrangement.SpaceAround,
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier
//            .fillMaxSize()
//            .background(color = MaterialTheme.colors.primary)
//    ) {
//        iconIds.forEachIndexed { index, item ->
//            BottomMenuTab(
//                modifier =
//                when (index) {
//                    1 -> {
//                        Modifier.padding(start = 0.dp, top = 0.dp, end = 40.dp, bottom = 0.dp)
//                    }
//                    2 -> {
//                        Modifier.padding(start = 40.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
//                    }
//                    else -> {
//                        Modifier
//                    }
//                },
//                iconId = item,
//                isSelected = index == selectedIndex
//            ) {
//                selectedIndex = index
//                onTabSelected(index)
//            }
//        }
//    }
//}

@ExperimentalComposeUiApi
@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        navController.popBackStack()
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = destination.iconId),
                        contentDescription = stringResource(destination.label)
                    )
                },
                label = {
                    Text(
                        text = stringResource(destination.label),
                        fontSize = 12.sp
                    )
                },
                alwaysShowLabel = false,
                selectedContentColor = MaterialTheme.colors.background,
                modifier =
                when (destination.direction) {
                    ArchivedTripsTabDestination -> {
                        Modifier.padding(end = 15.dp)
                    }
                    WishlistTabDestination -> {
                        Modifier.padding(start = 15.dp)
                    }
                    else -> {
                        Modifier
                    }
                }
            )
        }
    }
}

@Composable
fun BottomMenuTab(
    modifier: Modifier = Modifier,
    iconId: Int,
    isSelected: Boolean = false,
    onTabSelected: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight(0.75f)
            .clickable {
                onTabSelected()
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
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.surface,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun TopBar(
    openDrawer: () -> Unit
) {
    Row(
        Modifier
            .padding(16.dp)
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = {
                openDrawer()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        MyTextField(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
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
fun EmptySection() {
    Card(
        Modifier
            .height(64.dp)
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 10.dp)
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