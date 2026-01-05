package com.example.cookable.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Pill(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val clickableModifier =
        if (onClick != null) {
            modifier.clickable { onClick() }
        } else {
            modifier
        }

    androidx.compose.foundation.layout.Box(
        modifier =
            clickableModifier
                .background(backgroundColor, RoundedCornerShape(999.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        content()
    }
}
