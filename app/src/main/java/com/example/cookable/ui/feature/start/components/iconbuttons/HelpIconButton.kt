package com.example.cookable.ui.feature.start.components.iconbuttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun HelpIconButton(
    onClick: () -> Unit,
    tint: Color = PrimaryGreen,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Help,
            contentDescription = "Help button",
            tint = tint,
        )
    }
}
