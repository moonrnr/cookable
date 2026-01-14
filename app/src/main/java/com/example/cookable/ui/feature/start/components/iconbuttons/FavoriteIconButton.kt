package com.example.cookable.ui.feature.start.components.iconbuttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun FavoriteIconButton(
    onClick: () -> Unit,
    tint: Color = PrimaryGreen,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Favorite recipes list",
            tint = tint,
        )
    }
}
