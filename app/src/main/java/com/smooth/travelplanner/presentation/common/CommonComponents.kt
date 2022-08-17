package com.smooth.travelplanner.presentation.common

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smooth.travelplanner.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ExperimentalComposeUiApi
@Composable
internal fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    isPassword: Boolean,
    label: String,
    placeholder: String = "",
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isInputVisible by remember {
        if (isPassword) mutableStateOf(false) else mutableStateOf(true)
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = {
                    isInputVisible = !isInputVisible
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password_eye),
                        contentDescription = null,
                        tint = if (isInputVisible) MaterialTheme.colors.primary else Color.Gray
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
        visualTransformation = if (isInputVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier,
        keyboardOptions = if (isInputVisible) KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ) else KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
    )
}

@ExperimentalComposeUiApi
@Composable
internal fun MyStyledTextField(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType,
    textAlign: TextAlign,
    fontSize: Int,
    maxLines: Int,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        maxLines = maxLines,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        textStyle = TextStyle(
            fontSize = fontSize.sp,
            color = MaterialTheme.colors.primary,
            textAlign = textAlign
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
            cursorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = hint,
                fontSize = fontSize.sp,
                color = Color.LightGray,
                textAlign = textAlign,
                modifier = Modifier.fillMaxWidth()
            )
        }
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
internal fun TabHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.surface
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        color = color,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}


@Composable
fun TopRoundedTag(
    text: String,
    textColor: Color,
    fontSize: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color
) {
    Text(
        text = text,
        color = textColor,
        fontSize = fontSize.sp,
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(color = backgroundColor)
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 0.dp)
    )
}

@Composable
fun DatePickerBar(
    modifier: Modifier = Modifier,
    context: Context,
    label: String,
    fontSize: Int = 20,
    value: LocalDate,
    onValueChange: (LocalDate) -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onValueChange(LocalDate.of(year, month + 1, dayOfMonth))
        },
        value.year,
        value.monthValue - 1,
        value.dayOfMonth
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = label,
            color = MaterialTheme.colors.primary,
            fontSize = fontSize.sp
        )
        IconButton(
            onClick = {
                datePickerDialog.show()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_date),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.scale(1.5f)
            )
        }
    }
}

@Composable
fun TimePickerBar(
    modifier: Modifier = Modifier,
    context: Context,
    label: String,
    fontSize: Int = 20,
    value: LocalDateTime,
    onValueChange: (LocalDateTime) -> Unit
) {
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            onValueChange(
                LocalDateTime.of(
                    LocalDate.of(value.year, value.month, value.dayOfMonth),
                    LocalTime.of(hour, minute)
                )
            )
        },
        value.hour,
        value.minute,
        true
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = label,
            color = MaterialTheme.colors.primary,
            fontSize = fontSize.sp
        )
        IconButton(
            onClick = {
                timePickerDialog.show()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.scale(1.5f)
            )
        }
    }
}

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}