package com.smooth.travelplanner.ui.login

import androidx.compose.foundation.border
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smooth.travelplanner.R

@ExperimentalComposeUiApi
@Composable
internal fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    isPassword: Boolean,
    label: String,
    placeholder: String = "",
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val input = remember {
        mutableStateOf("")
    }
    val isInputVisible = remember {
        if (isPassword) mutableStateOf(false) else mutableStateOf(true)
    }

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
        modifier = modifier,
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
internal fun MyButton(
    modifier: Modifier = Modifier,
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
        modifier = modifier
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

@Composable
internal fun RememberMeSection(
    modifier: Modifier = Modifier
) {
    val checked = remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
            },
            modifier = Modifier,
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Remember me",
            color = MaterialTheme.colors.onSurface
        )
    }
}