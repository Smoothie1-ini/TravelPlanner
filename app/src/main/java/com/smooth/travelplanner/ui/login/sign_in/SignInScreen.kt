package com.smooth.travelplanner.ui.login.sign_in

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.ui.common.MyButton
import com.smooth.travelplanner.ui.common.MyOutlinedTextField
import com.smooth.travelplanner.ui.destinations.HomeScreenDestination
import com.smooth.travelplanner.ui.destinations.PasswordResetScreenDestination
import com.smooth.travelplanner.ui.destinations.SignUpScreenDestination
import com.smooth.travelplanner.ui.login.RememberMeSection
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@RootNavGraph(start = true)
@Destination
@Composable
fun SignInScreen(
    navigator: DestinationsNavigator,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val signInData = viewModel.signInData.collectAsState()

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val color = MaterialTheme.colors.background
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    LaunchedEffect(key1 = viewModel.signInState) {
        viewModel.signInState.collectLatest {
            when (it) {
                is Response.Success -> {
                    //TODO bug; LaunchedEffect is signing in again immediately
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    navigator.navigate(HomeScreenDestination)
                }
                is Response.Error -> {
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Response.Message -> {
                    Toast.makeText(context, "Message: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }
                is Response.Empty -> {
                    Log.d("SignInScreen", "ScreenState: No state received so far.")
                }
            }
        }
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
                    label = "Email Address",
                    value = signInData.value.email,
                    onValueChange = viewModel::onEmailChange
                )
                MyOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    isPassword = true,
                    label = "Password",
                    value = signInData.value.password,
                    onValueChange = viewModel::onPasswordChange
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .offset(y = (-7.5).dp)
                ) {
                    RememberMeSection(
                        modifier = Modifier,
                        value = signInData.value.rememberMe,
                        onValueChanged = viewModel::onRememberMeChange
                    )
                    Text(
                        text = "Forgot password?",
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .clickable {
                                navigator.navigate(PasswordResetScreenDestination)
                            }
                    )
                }
                MyButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = "Sign in",
                    backgroundColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.background
                ) {
                    viewModel.validateData(signInData.value.email, signInData.value.password)
                }
                Spacer(modifier = Modifier.height(10.dp))
                MyButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = "Sign in with Google",
                    backgroundColor = MaterialTheme.colors.background,
                    textColor = MaterialTheme.colors.primary
                ) {
                    //TODO sign in with google
                    navigator.navigate(HomeScreenDestination)
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