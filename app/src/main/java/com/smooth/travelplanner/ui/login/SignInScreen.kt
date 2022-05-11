package com.smooth.travelplanner.ui.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.destinations.HomeScreenDestination
import com.smooth.travelplanner.ui.destinations.SignUpScreenDestination

@ExperimentalComposeUiApi
@Destination(start = true)
@Composable
fun SignInScreen(
    navigator: DestinationsNavigator
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val color = MaterialTheme.colors.background

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    Surface(color = MaterialTheme.colors.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.signin_image4),
                contentDescription = "SignInImage",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxHeight(0.33f)
                    .padding(20.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(MaterialTheme.colors.surface)
                    .verticalScroll(rememberScrollState())
                    .weight(1f, fill = false)
            ) {
                Text(
                    text = "Welcome back, traveller!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(top = 40.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    isPassword = false,
                    label = "Email Address"
                )
                MyOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    isPassword = true,
                    label = "Password"
                )
                RememberMeSection(modifier = Modifier.fillMaxWidth(0.8f))
                MyButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = "Sign in",
                    backgroundColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.background
                ) {
                    navigator.popBackStack()
                    navigator.navigate(HomeScreenDestination)
                }

                Spacer(modifier = Modifier.height(10.dp))
                MyButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = "Sign in with Google",
                    backgroundColor = MaterialTheme.colors.background,
                    textColor = MaterialTheme.colors.primary
                ) {

                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Don't have an account?",
                    color = MaterialTheme.colors.onSurface,
                )
                Text(
                    text = "Sign up!",
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .clickable {
                            // TODO Single top not working anywhere
                            navigator.navigate(SignUpScreenDestination) {
                                launchSingleTop = true
                            }
                        }
                        .padding(5.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}