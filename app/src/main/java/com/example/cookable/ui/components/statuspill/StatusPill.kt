package com.example.cookable.ui.components.statuspill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusPill(
    title: String,
    count: Int,
    backgroundColor: Color,
    textColor: Color,
) {
    Box(
        modifier =
            Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(999.dp),
                ).padding(horizontal = 14.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "$title: $count",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
        )
    }
}
