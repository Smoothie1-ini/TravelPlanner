package com.smooth.travelplanner.ui.home.main_tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smooth.travelplanner.R

@Composable
fun TabHeader(
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

@Preview
@Composable
fun TripItem(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(170.dp)
            .padding(start = 16.dp, bottom = 20.dp)
            .clickable {

            },
        shape = RoundedCornerShape(topStart = 75.dp, bottomStart = 75.dp),
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant)
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