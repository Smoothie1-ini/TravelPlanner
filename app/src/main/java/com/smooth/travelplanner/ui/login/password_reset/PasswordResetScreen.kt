package com.smooth.travelplanner.ui.login.password_reset

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.ui.MyButton
import com.smooth.travelplanner.ui.MyOutlinedTextField
import com.smooth.travelplanner.ui.destinations.SignInScreenDestination
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun PasswordResetScreen(
    navigator: DestinationsNavigator,
    viewModel: PasswordResetViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val passwordResetData = viewModel.passwordResetData.collectAsState()

    LaunchedEffect(key1 = viewModel.passwordResetState) {
        viewModel.passwordResetState.collectLatest {
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
                    Log.d("PasswordResetScreen", "ScreenState: No state received so far.")
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
                painter = painterResource(id = R.drawable.signin_image2),
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
                    text = "Reset your password.",
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
                    value = passwordResetData.value.email,
                    onValueChanged = viewModel::onEmailChanged
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = "Reset password",
                    backgroundColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.background
                ) {
                    viewModel.validateData(passwordResetData.value.email)
                }
            }
        }
    }
}