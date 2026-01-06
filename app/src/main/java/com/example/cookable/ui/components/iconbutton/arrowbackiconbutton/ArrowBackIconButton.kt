package com.example.cookable.ui.components.iconbutton.arrowbackiconbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ArrowBackIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Arrow back button")
    }
}
