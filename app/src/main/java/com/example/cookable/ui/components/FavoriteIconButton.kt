package com.example.cookable.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.cookable.ui.theme.Primary

@Composable
fun FavoriteIconButton(
    isFavorite: Boolean,
    isSmall: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sizeModifier =
        if (isSmall) {
            Modifier.size(36.dp)
        } else {
            Modifier
        }

    IconButton(
        onClick = onClick,
        modifier =
            modifier
                .background(
                    color = White.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(50),
                ).then(sizeModifier),
    ) {
        Icon(
            imageVector =
                if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Outlined.FavoriteBorder
                },
            contentDescription = "Add to favorites",
            tint = Primary,
        )
    }
}
