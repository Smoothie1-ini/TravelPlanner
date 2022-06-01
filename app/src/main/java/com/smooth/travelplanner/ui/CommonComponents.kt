package com.smooth.travelplanner.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.smooth.travelplanner.R

@ExperimentalComposeUiApi
@Composable
internal fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    isPassword: Boolean,
    label: String,
    placeholder: String = "",
    value: String,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isInputVisible by remember {
        if (isPassword) mutableStateOf(false) else mutableStateOf(true)
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChanged(it)
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
    textAlign: TextAlign,
    fontSize: Int,
    maxLines: Int,
    hint: String
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var input by remember {
        mutableStateOf("")
    }

    TextField(
        value = input,
        onValueChange = {
            input = it
        },
        maxLines = maxLines,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
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
internal fun EmptySection() {
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
internal fun TabHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.primary
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        color = color,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@ExperimentalComposeUiApi
@Composable
internal fun Trip(
    modifier: Modifier = Modifier,
    onTripSelected: () -> Unit,
    onTripDeleted: () -> Unit,
) {
    Card(
        modifier = modifier
            .height(160.dp)
            .padding(start = 16.dp, bottom = 10.dp)
            .clickable {
                onTripSelected()
            },
        shape = RoundedCornerShape(topStart = 75.dp, bottomStart = 75.dp),
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.black_square),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .padding(10.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxHeight()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "This is a title.",
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        maxLines = 1
                    )
                    IconButton(
                        onClick = {
                            onTripDeleted()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primaryVariant
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                        modifier = Modifier.padding(
                            start = 0.dp,
                            top = 0.dp,
                            end = 10.dp,
                            bottom = 0.dp
                        ),
                        color = MaterialTheme.colors.primary,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Justify,
                        maxLines = 4
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "20.05.22 - 23.05.22",
                                color = MaterialTheme.colors.surface,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                                    .background(color = MaterialTheme.colors.primaryVariant)
                                    .padding(start = 9.dp, top = 5.dp, end = 9.dp, bottom = 0.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}