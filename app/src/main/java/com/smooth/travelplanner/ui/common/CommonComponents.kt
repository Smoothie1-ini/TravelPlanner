package com.smooth.travelplanner.ui.common

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import java.time.LocalDate

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

@ExperimentalComposeUiApi
@Composable
internal fun Trip(
    modifier: Modifier = Modifier,
    onTripSelect: () -> Unit,
    onTripDelete: () -> Unit,
) {
    Card(
        modifier = modifier
            .height(170.dp)
            .padding(start = 16.dp, bottom = 15.dp)
            .clickable {
                onTripSelect()
            },
        shape = RoundedCornerShape(topStart = 75.dp, bottomStart = 75.dp),
        backgroundColor = Color.White,
//        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
        elevation = 7.5.dp
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
                    .size(155.dp)
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
                        modifier = Modifier.padding(top = 0.dp),
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        maxLines = 1
                    )
                    IconButton(
                        onClick = {
                            onTripDelete()
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
                        maxLines = 5
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp)
                    ) {
                        TopRoundedTag(
                            text = "20.05.22 - 23.05.22",
                            textColor = MaterialTheme.colors.surface,
                            fontSize = 12,
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        )
                        TopRoundedTag(
                            text = "11800 zł",
                            textColor = MaterialTheme.colors.surface,
                            fontSize = 12,
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun TripDay(
    modifier: Modifier = Modifier,
    onTripDaySelect: () -> Unit,
    onTripDayDelete: () -> Unit
) {
    //TODO Card height dependent on its content
    Card(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth(0.9f)
            .padding(top = 15.dp)
            .clickable {
                onTripDaySelect()
            },
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        //border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
        elevation = 7.5.dp
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
            Column {
                Text(
                    text = "Monday (23.05.2022r)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                LazyColumn() {
                    item {
                        Text(
                            text = "12:20  Wawel",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    item {
                        Text(
                            text = "14:30  Sukiennice",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    item {
                        Text(
                            text = "17:00  Wieża mariacka",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    item {
                        Text(
                            text = "19:00  Wisełka",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    onTripDayDelete()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}

@Composable
internal fun TripEvent(
    modifier: Modifier = Modifier,
    onTripEventSelect: () -> Unit,
    onTripEventNavigate: () -> Unit,
    onTripEventDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth(0.9f)
            .padding(top = 15.dp)
            .clickable {
                onTripEventSelect()
            },
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        //border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
        elevation = 7.5.dp
    ) {
        Row {
            Column(
                modifier = Modifier.width(145.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.black_square),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(145.dp)
                        .clip(RectangleShape)
                )
                LazyRow(
                    Modifier
                        .background(MaterialTheme.colors.primaryVariant)
                        .width(145.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(count = 5) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_empty_star),
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Column(
                        Modifier
                            .weight(3f)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "This is a title.",
                            modifier = Modifier.padding(bottom = 5.dp),
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            maxLines = 1
                        )
                        Text(
                            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                            color = MaterialTheme.colors.primary,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Justify,
                            maxLines = 6
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                    ) {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null,
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_navigate),
                                contentDescription = null,
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    TopRoundedTag(
                        text = "15:20",
                        textColor = MaterialTheme.colors.surface,
                        fontSize = 12,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    )
                    TopRoundedTag(
                        text = "1h 30m",
                        textColor = MaterialTheme.colors.surface,
                        fontSize = 12,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    )
                    TopRoundedTag(
                        text = "170zł",
                        textColor = MaterialTheme.colors.surface,
                        fontSize = 12,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    )
                }
            }
        }
    }
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
    value: LocalDate,
    onValueChange: (LocalDate) -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onValueChange(LocalDate.of(year, month, dayOfMonth))
        },
        value.year,
        value.monthValue,
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
            fontSize = 20.sp
        )
        IconButton(
            onClick = {
                datePickerDialog.show()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.scale(1.5f)
            )
        }
    }
}