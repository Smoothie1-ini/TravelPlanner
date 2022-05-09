package com.smooth.travelplanner.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.R
import com.smooth.travelplanner.destinations.HomeScreenDestination
import com.smooth.travelplanner.destinations.SignUpScreenDestination

@ExperimentalComposeUiApi
@Destination(start = true)
@Composable
fun SignInScreen(
    navigator: DestinationsNavigator
) {
    Surface(color = MaterialTheme.colors.surface) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.signin_image4),
                contentDescription = "SignInImage",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxHeight(0.33f).padding(20.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(rememberScrollState())
                    .weight(1f, fill = false)
            ) {
                Text(
                    text = "Welcome back, traveller!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(top = 40.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyOutlinedTextField(isPassword = false, label = "Email Address")
                MyOutlinedTextField(isPassword = true, label = "Password")
                Spacer(modifier = Modifier.height(20.dp))
                MyButton(text = "Sign in", backgroundColor = MaterialTheme.colors.primary, textColor = MaterialTheme.colors.background) {
                    navigator.navigate(HomeScreenDestination) {
                        navigator.popBackStack()
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                MyButton(text = "Sign in with Google", backgroundColor = MaterialTheme.colors.background, textColor = MaterialTheme.colors.primary) {

                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Don't have an account?",
                    color = MaterialTheme.colors.onSurface,
                )
                Text(
                    text = "Sign up!",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .clickable {
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

@ExperimentalComposeUiApi
@Composable
fun MyOutlinedTextField(
    isPassword: Boolean,
    label: String,
    placeholder: String = "",
    ) {
    val input = remember {
        mutableStateOf("")
    }
    val isInputVisible = remember {
        if (isPassword) mutableStateOf(false) else mutableStateOf(true)
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = input.value,
        onValueChange = {
            input.value = it
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = {
                    isInputVisible.value = !isInputVisible.value
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password_eye),
                        contentDescription = null,
                        tint = if (isInputVisible.value) MaterialTheme.colors.primary else Color.Gray
                    )
                }
            }
        },
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        singleLine = true,
        visualTransformation = if (isInputVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth(0.8f),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
    )
}

@Composable
fun MyButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .border(1.dp, textColor, RoundedCornerShape(4.dp))
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = textColor
        )
    }
}