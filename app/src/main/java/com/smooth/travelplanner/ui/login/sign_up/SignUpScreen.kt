package com.smooth.travelplanner.ui.login.sign_up

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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.ui.common.MyButton
import com.smooth.travelplanner.ui.common.MyOutlinedTextField
import com.smooth.travelplanner.ui.destinations.SignInScreenDestination
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val signUpData = viewModel.signUpData.collectAsState()

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val color = MaterialTheme.colors.background
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    LaunchedEffect(key1 = viewModel.signUpState) {
        viewModel.signUpState.collectLatest {
            when (it) {
                is Response.Success -> {
                    //TODO bug; LaunchedEffect is signing in again immediately
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    navigator.navigate(SignInScreenDestination)
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
                    Log.d("SignUpScreen", "ScreenState: No state received so far.")
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
                painter = painterResource(id = R.drawable.signin_image10),
                contentDescription = "SignUpImage",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxHeight(0.279f)
                    .padding(10.dp)
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
                    text = "Create an account.",
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
                    label = "Name",
                    value = signUpData.value.name,
                    onValueChanged = viewModel::onNameChanged
                )
                MyOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    isPassword = false,
                    label = "Email Address",
                    value = signUpData.value.email,
                    onValueChanged = viewModel::onEmailChanged
                )
                MyOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    isPassword = true,
                    label = "Password",
                    value = signUpData.value.password,
                    onValueChanged = viewModel::onPasswordChanged
                )
                MyOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    isPassword = true,
                    label = "Confirm Password",
                    value = signUpData.value.repeatPassword,
                    onValueChanged = viewModel::onRepeatPasswordChanged
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = "Sign up",
                    backgroundColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.background
                ) {
                    viewModel.validateData(
                        signUpData.value.email,
                        signUpData.value.password,
                        signUpData.value.repeatPassword
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Already have an account?",
                    color = MaterialTheme.colors.onSurface,
                )
                Text(
                    text = "Sign in!",
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .clickable {
                            navigator.popBackStack()
                        }
                        .padding(5.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}