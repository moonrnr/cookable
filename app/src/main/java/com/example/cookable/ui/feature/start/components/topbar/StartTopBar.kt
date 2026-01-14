package com.example.cookable.ui.feature.start.components.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookable.ui.feature.start.components.applogo.AppLogo
import com.example.cookable.ui.feature.start.components.iconbuttons.FavoriteIconButton
import com.example.cookable.ui.feature.start.components.iconbuttons.HelpIconButton

@Composable
fun StartTopBar(
    onHelpClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 0.dp, bottom = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        AppLogo()

        Row {
            HelpIconButton(
                onClick = onHelpClick,
            )
            FavoriteIconButton(onClick = onFavoritesClick)
        }
    }
}
