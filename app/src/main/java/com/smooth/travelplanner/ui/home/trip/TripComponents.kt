package com.smooth.travelplanner.ui.home.trip

import androidx.compose.foundation.Image
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

@Preview
@Composable
fun TripItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Card(
            modifier = modifier
                .height(150.dp)
                .padding(start = 16.dp)
                .clickable {

                },
            shape = RoundedCornerShape(topStart = 75.dp, bottomStart = 75.dp),
            backgroundColor = Color.White
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
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "This is a title.",
                            modifier = Modifier.padding(10.dp),
                            color = MaterialTheme.colors.primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            maxLines = 1
                        )
                        IconButton(
                            onClick = {

                            },
                            modifier = Modifier
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null,
                                tint = MaterialTheme.colors.secondary
                            )
                        }
                    }
                    Text(
                        text = "There are also a number of extension functions which allow you to add collections of items, such as a List.",
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colors.secondary,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Justify,
                        maxLines = 4
                    )
                }
            }
        }
    }
}