package com.smooth.travelplanner.presentation.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.smooth.travelplanner.R
import com.smooth.travelplanner.presentation.NavGraphs
import com.smooth.travelplanner.presentation.appCurrentDestinationAsState
import com.smooth.travelplanner.presentation.destinations.*
import com.smooth.travelplanner.presentation.startAppDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val iconId: Int,
    @StringRes val label: Int
) {
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    CurrentTripsTab(
        CurrentTripsTabDestination,
        R.drawable.ic_current,
        R.string.current_trips_tab
    ),

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    ArchivedTripsTab(
        ArchivedTripsTabDestination,
        R.drawable.ic_archive,
        R.string.archived_trips_tab
    ),
    WishlistTab(
        WishlistTabDestination,
        R.drawable.ic_wishlist,
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
    visible: Boolean,
    openDrawer: () -> Unit,
    searchBarValue: String,
    onSearchBarValueChanged: (String) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .padding(
                        start = 10.dp,
                        top = 0.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
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
                SearchBar(
                    modifier = Modifier.weight(1f),
                    value = searchBarValue,
                    onValueChanged = onSearchBarValueChanged
                )
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
    }
}

@ExperimentalAnimationApi
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
                    navController.navigate(destination.direction) {
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
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
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
            onValueChanged(it)
        }
    )
}

@Composable
fun Drawer(
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCurrentClick: () -> Unit,
    onArchivedClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAboutClick: () -> Unit,
    onFeedbackClick: () -> Unit
) {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        DrawerHeader(
            onLogoutClick = onLogoutClick,
            onSettingsClick = onSettingsClick
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            DrawerMenu(
                onCurrentClick = onCurrentClick,
                onArchivedClick = onArchivedClick,
                onWishlistClick = onWishlistClick,
                onProfileClick = onProfileClick,
                onAboutClick = onAboutClick,
                onFeedbackClick = onFeedbackClick,
            )
            DrawerFooter()
        }
    }
}

@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        IconButton(
            onClick = {
                onLogoutClick()
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .scale(1.5f)
                    .padding(20.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.hage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(175.dp)
                .padding(10.dp)
                .clip(CircleShape)
        )
        IconButton(
            onClick = {
                onSettingsClick()
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .scale(1.5f)
                    .padding(20.dp)
            )
        }
    }
}

@Composable
fun DrawerMenu(
    modifier: Modifier = Modifier,
    onCurrentClick: () -> Unit,
    onArchivedClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAboutClick: () -> Unit,
    onFeedbackClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ) {
        DrawerMenuItem(
            iconId = R.drawable.ic_current,
            text = "Current trips",
            onClick = onCurrentClick
        )
        DrawerMenuItem(
            iconId = R.drawable.ic_archive,
            text = "Archived trips",
            onClick = onArchivedClick
        )
        DrawerMenuItem(
            iconId = R.drawable.ic_wishlist,
            text = "Events wishlist",
            onClick = onWishlistClick
        )
        DrawerMenuItem(
            iconId = R.drawable.ic_profile,
            text = "Your profile",
            onClick = onProfileClick
        )
        DrawerMenuItem(
            iconId = R.drawable.ic_about,
            text = "About us",
            onClick = onAboutClick
        )
        DrawerMenuItem(
            iconId = R.drawable.ic_feedback,
            text = "Leave us feedback",
            onClick = onFeedbackClick
        )
    }
}

@Composable
fun DrawerMenuItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(top = 10.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        elevation = 5.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .scale(1.2f)
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(10.dp)
            )
        }

    }
}

@Composable
fun DrawerFooter(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "com.smooth",
            color = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = "V.1.0.0",
            color = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}

@Composable
fun ConfirmCancelDialog(
    visible: Boolean,
    onValueChanged: (Boolean) -> Unit,
    title: String,
    text: String
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 24.sp
                )
            },
            confirmButton = {
                Button(onClick = {
                    onValueChanged(true)
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(onClick = {
                    onValueChanged(false)
                }) {
                    Text(text = "Dismiss")
                }
            },
            text = {
                Text(
                    text = text,
                    color = MaterialTheme.colors.primary,
                    fontSize = 16.sp
                )
            }
        )
    }
}