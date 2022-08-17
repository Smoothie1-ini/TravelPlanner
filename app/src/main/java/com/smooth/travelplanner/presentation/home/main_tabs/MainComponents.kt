package com.smooth.travelplanner.presentation.home.main_tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.model.TripEvent
import com.smooth.travelplanner.presentation.common.TopRoundedTag
import com.smooth.travelplanner.util.*

@ExperimentalComposeUiApi
@Composable
internal fun Trip(
    modifier: Modifier = Modifier,
    onTripSelect: () -> Unit,
    onDeleteDialogChange: (Trip) -> Unit,
    trip: Trip
) {
    Card(
        modifier = modifier
            .height(170.dp)
            .padding(start = 10.dp, bottom = 15.dp)
            .clickable {
                onTripSelect()
            },
        shape = RoundedCornerShape(topStart = 75.dp, bottomStart = 75.dp),
        backgroundColor = Color.White,
        elevation = 7.5.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.hage),
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
                    .padding(start = 5.dp)
                    .fillMaxHeight()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = trip.title,
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        maxLines = 2
                    )
                    IconButton(
                        onClick = {
                            onDeleteDialogChange(trip)
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
                        text = trip.description,
                        modifier = Modifier.padding(
                            start = 0.dp,
                            top = 0.dp,
                            end = 10.dp,
                            bottom = 0.dp
                        ),
                        color = MaterialTheme.colors.primary,
                        fontSize = 11.5.sp,
                        textAlign = TextAlign.Justify,
                        maxLines = 4
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp)
                    ) {
                        if (trip.tripDays.isNotEmpty())
                            TopRoundedTag(
                                text = "${
                                    trip.getFirstDay()?.toShortDateString()
                                } - ${trip.getLastDay()?.toShortDateString()}",
                                textColor = MaterialTheme.colors.surface,
                                fontSize = 11,
                                backgroundColor = MaterialTheme.colors.primaryVariant
                            )
                        else
                            TopRoundedTag(
                                text = "",
                                textColor = Color.Transparent,
                                fontSize = 11,
                                backgroundColor = Color.Transparent
                            )
                        if (trip.cost != 0)
                            TopRoundedTag(
                                text = (trip.cost / 100).toString() + " zł",
                                textColor = MaterialTheme.colors.surface,
                                fontSize = 11,
                                backgroundColor = MaterialTheme.colors.primaryVariant
                            )
                        else
                            TopRoundedTag(
                                text = "",
                                textColor = Color.Transparent,
                                fontSize = 11,
                                backgroundColor = Color.Transparent
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
    onTripDayDelete: () -> Unit,
    tripDay: TripDay
) {
    //TODO Card height dependent on tripEvents count
    Card(
        modifier = modifier
            .height(160.dp)
            .fillMaxWidth(0.9f)
            .padding(top = 15.dp)
            .clickable {
                onTripDaySelect()
            },
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        elevation = 7.5.dp
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, end = 10.dp)
        ) {
            Column {
                Text(
                    text = "(${tripDay.date.toLongDateString()}r.)    ${tripDay.date.toDayOfTheWeek()}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                LazyColumn {
                    items(tripDay.tripEvents) {
                        Text(
                            text = "${it.time.toShortTimeString()}    ${it.title}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxHeight()
            ) {
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
                TopRoundedTag(
                    text = (tripDay.cost / 100).toString() + " zł",
                    textColor = MaterialTheme.colors.surface,
                    fontSize = 11,
                    backgroundColor = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}

@Composable
internal fun TripEvent(
    modifier: Modifier = Modifier,
    onTripEventSelect: () -> Unit,
    onTripEventDelete: () -> Unit,
    onTripEventFavorite: () -> Unit,
    onTripEventNavigate: () -> Unit,
    tripEvent: TripEvent
) {
    Card(
        modifier = modifier
            .height(180.dp)
            .fillMaxWidth(0.9f)
            .padding(bottom = 15.dp)
            .clickable {
                onTripEventSelect()
            },
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        elevation = 7.5.dp
    ) {
        Row {
            Column(
                modifier = Modifier.width(145.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hage),
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
                    items(count = tripEvent.rating) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_full_star),
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    items(count = 5 - tripEvent.rating) {
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
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                    ) {
                        Text(
                            text = tripEvent.title,
                            modifier = Modifier.padding(bottom = 5.dp),
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            maxLines = 3
                        )
                        Text(
                            text = tripEvent.description,
                            color = MaterialTheme.colors.primary,
                            fontSize = 11.5.sp,
                            textAlign = TextAlign.Justify,
                            maxLines = 4
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp)
                    ) {
                        IconButton(
                            onClick = {
                                onTripEventDelete()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null,
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                        IconButton(
                            onClick = {
                                onTripEventFavorite()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_empty_favorite),
                                contentDescription = null,
                                tint = MaterialTheme.colors.primaryVariant
                            )
                        }
                        IconButton(
                            onClick = {
                                onTripEventNavigate()
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
                        text = tripEvent.time.toShortTimeString(),
                        textColor = MaterialTheme.colors.surface,
                        fontSize = 11,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    )
                    TopRoundedTag(
                        text = "${tripEvent.duration.toHoursAndMinutes().first}h ${tripEvent.duration.toHoursAndMinutes().second}m",
                        textColor = MaterialTheme.colors.surface,
                        fontSize = 11,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    )
                    TopRoundedTag(
                        text = "${tripEvent.cost / 100} zł",
                        textColor = MaterialTheme.colors.surface,
                        fontSize = 11,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    )
                }
            }
        }
    }
}