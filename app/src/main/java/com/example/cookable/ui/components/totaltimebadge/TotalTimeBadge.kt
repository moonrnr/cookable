package com.example.cookable.ui.components.totaltimebadge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.DarkGrey
import com.example.cookable.ui.theme.LightGrey

@Composable
fun TotalTimeBadge(totalTime: String) {
    Box(
        modifier =
            Modifier
                .background(
                    color = LightGrey,
                    shape = RoundedCornerShape(999.dp),
                ).padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "⏱ $totalTime",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DarkGrey,
        )
    }
}
