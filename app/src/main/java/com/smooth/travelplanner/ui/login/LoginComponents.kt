package com.smooth.travelplanner.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
        )
        Spacer(modifier = Modifier.width(2.5.dp))
        Text(
            text = "Remember me",
            color = MaterialTheme.colors.onSurface,
            fontSize = 14.sp,
        )
    }
}