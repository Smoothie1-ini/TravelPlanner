package com.smooth.travelplanner.presentation.home.trip_event_details

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chargemap.compose.numberpicker.NumberPicker
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.smooth.travelplanner.R
import com.smooth.travelplanner.presentation.common.MyStyledTextField
import com.smooth.travelplanner.presentation.common.TimePickerBar
import com.smooth.travelplanner.presentation.common.multi_fab.FabIcon
import com.smooth.travelplanner.presentation.common.multi_fab.MultiFloatingActionButton
import com.smooth.travelplanner.presentation.common.multi_fab.fabOption
import com.smooth.travelplanner.presentation.common.multi_fab.rememberMultiFabState

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Destination
@Composable
fun TripEventDetailsScreen(
    tripEventId: String = "",
    viewModel: TripEventDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val tripEventDetailsData = viewModel.tripEventDetailsData.collectAsState()

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
                    fabIcon = FabIcon(R.drawable.ic_edit, 260f),
                    onFabItemClicked = {
                        when (it.id) {
                            0 -> {
                                viewModel.onFabSaveTripEventClicked()
                            }
                            1 -> {
                                Log.d("TripEventDetailsScreen", "i don't know yet")
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hage),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .height(200.dp)
                        .clip(RectangleShape)
                        .clickable {
                            viewModel.onImageChange("some image url")
                        }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyStyledTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    keyboardType = KeyboardType.Text,
                    textAlign = TextAlign.Center,
                    fontSize = 26,
                    maxLines = 1,
                    hint = "Title $tripEventId",
                    value = tripEventDetailsData.value.title,
                    onValueChange = viewModel::onTitleChange
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyStyledTextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    keyboardType = KeyboardType.Text,
                    textAlign = TextAlign.Center,
                    fontSize = 16,
                    maxLines = 4,
                    hint = "Description",
                    value = tripEventDetailsData.value.description,
                    onValueChange = viewModel::onDescriptionChange
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TimePickerBar(
                        modifier = Modifier.weight(1f),
                        context = context,
                        label = tripEventDetailsData.value.timeLabel,
                        fontSize = 18,
                        value = tripEventDetailsData.value.time,
                        onValueChange = viewModel::onTimeChange
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        NumberPicker(
                            value = tripEventDetailsData.value.durationHours,
                            onValueChange = viewModel::onDurationHoursChange,
                            range = (0..24)
                        )
                        Text(text = "H")
                        NumberPicker(
                            value = tripEventDetailsData.value.durationMinutes,
                            onValueChange = viewModel::onDurationMinutesChange,
                            range = (0..60)
                        )
                        Text(text = "M")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rate event",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )
                    RatingBar(
                        modifier = Modifier.weight(1.4f),
                        value = tripEventDetailsData.value.rating,
                        config = RatingBarConfig()
                            .activeColor(MaterialTheme.colors.primary)
                            .inactiveColor(MaterialTheme.colors.background)
                            .style(RatingBarStyle.HighLighted)
                            .size(36.dp),
                        onValueChange = viewModel::onRatingChange,
                        onRatingChanged = { rating ->
                            Log.d("TAG", "onRatingChanged: $rating")
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Set cost",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1.4f)
                    )
                    MyStyledTextField(
                        modifier = Modifier
                            .weight(1f),
                        keyboardType = KeyboardType.Decimal,
                        textAlign = TextAlign.Start,
                        fontSize = 18,
                        maxLines = 1,
                        hint = "Cost",
                        value = if (tripEventDetailsData.value.cost == null) "" else tripEventDetailsData.value.cost.toString(),
                        onValueChange = viewModel::onCostChange
                    )
                    Text(
                        text = "Zł",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Set location",
                    color = MaterialTheme.colors.primary,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.map),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(250.dp)
                        .clip(RectangleShape)
                        .padding(bottom = 20.dp)
                        .clickable {

                        }
                )
            }
        }
    }
}