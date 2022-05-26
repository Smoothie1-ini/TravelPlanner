package com.smooth.travelplanner.ui.home.main_tabs.current_trips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smooth.travelplanner.R

@Composable
internal fun TripDay(
    modifier: Modifier = Modifier
) {
    //TODO Card height dependent on its content
    Card(
        modifier = modifier
            .height(140.dp)
            .fillMaxWidth(0.9f)
            .padding(top = 15.dp)
            .clickable {

            },
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
        elevation = 5.dp
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
                    color = MaterialTheme.colors.primaryVariant
                )
                Spacer(modifier = Modifier.height(5.dp))
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