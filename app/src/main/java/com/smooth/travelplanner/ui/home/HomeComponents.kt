package com.smooth.travelplanner.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.EmptySection
import com.smooth.travelplanner.ui.NavGraphs
import com.smooth.travelplanner.ui.appCurrentDestinationAsState
import com.smooth.travelplanner.ui.destinations.*
import com.smooth.travelplanner.ui.startAppDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val iconId: Int,
    @StringRes val label: Int
) {
    @ExperimentalComposeUiApi
    CurrentTripsTab(
        CurrentTripsTabDestination,
        R.drawable.ic_home,
        R.string.current_trips_tab
    ),
    @ExperimentalComposeUiApi
    ArchivedTripsTab(
        ArchivedTripsTabDestination,
        R.drawable.ic_archive,
        R.string.archived_trips_tab
    ),
    WishlistTab(
        WishlistTabDestination,
        R.drawable.ic_favorite,
        R.string.wishlist_tab
    ),
    ProfileTab(
        ProfileTabDestination,
        R.drawable.ic_profile,
        R.string.profile_tab
    ),
}

@ExperimentalComposeUiApi
@Composable
fun TopBar(
    state: MutableState<Boolean>,
    openDrawer: () -> Unit
) {
    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
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
                SearchBar(modifier = Modifier.weight(1f))
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
            EmptySection()
        }
    }
}

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
                        Modifier.padding(end = 10.dp)
                    }
                    WishlistTabDestination -> {
                        Modifier.padding(start = 10.dp)
                    }
                    else -> {
                        Modifier
                    }
                }
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
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