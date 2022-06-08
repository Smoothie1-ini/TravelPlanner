package com.smooth.travelplanner.ui.home.main_tabs

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.common.multi_fab.FabIcon
import com.smooth.travelplanner.ui.common.multi_fab.MultiFloatingActionButton
import com.smooth.travelplanner.ui.common.multi_fab.fabOption
import com.smooth.travelplanner.ui.common.multi_fab.rememberMultiFabState
import java.time.LocalDate

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun TripDayDetailsScreen(
    tripId: String = "",
    viewModel: TripDayDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val tripDayDetailsData = viewModel.tripDayDetailsData.collectAsState()

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = MaterialTheme.colors.primary
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    val multiFabState = rememberMultiFabState()

    Surface {
        Scaffold(
            backgroundColor = Color.Transparent,
            floatingActionButton = {
                MultiFloatingActionButton(
                    items = viewModel.fabItems,
                    fabState = multiFabState,
                    fabIcon = FabIcon(R.drawable.ic_add, 135f),
                    onFabItemClicked = {
                        when (it.id) {
                            0 -> {
                                viewModel.onFabSaveTripDayClicked()
                            }
                            1 -> {
                                Log.d("TripDetailsScreen", "navigate to DayDetailsScreen")
                            }
                        }
                    },
                    fabOption = fabOption(
                        backgroundTint = MaterialTheme.colors.primaryVariant,
                        iconTint = MaterialTheme.colors.background,
                        showLabel = true
                    )
                )
            },
            isFloatingActionButtonDocked = false,
            floatingActionButtonPosition = FabPosition.End
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = it
            ) {
                item {
                    DatePickerBar(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(top = 15.dp),
                        context = context,
                        label = tripDayDetailsData.value.dateLabel,
                        value = tripDayDetailsData.value.date,
                        onValueChange = viewModel::onDateChange
                    )
                }
            }
        }
    }
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