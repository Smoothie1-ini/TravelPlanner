package com.smooth.travelplanner.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
internal fun RememberMeSection(
    modifier: Modifier = Modifier,
    value: Boolean,
    onValueChanged: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Checkbox(
            checked = value,
            onCheckedChange = {
                onValueChanged()
            },
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
        )
        Text(
            text = "Remember me",
            color = MaterialTheme.colors.onSurface,
            fontSize = 13.sp,
        )
    }
}