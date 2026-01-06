package com.example.cookable.ui.components.matchbadge

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
import com.example.cookable.core.util.transformMatchLevelToTextAndBackgroundColors
import com.example.cookable.domain.model.MatchLevel

@Composable
fun MatchBadge(
    matchPercentage: Int,
    level: MatchLevel,
) {
    val (backgroundColor, textColor) =
        transformMatchLevelToTextAndBackgroundColors(level)

    Box(
        modifier =
            Modifier
                .background(backgroundColor, RoundedCornerShape(999.dp))
                .padding(horizontal = 14.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = matchLevelToText(matchPercentage, level),
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}

private fun matchLevelToText(
    match: Int,
    level: MatchLevel,
): String =
    when (level) {
        MatchLevel.PERFECT -> "100% · Perfect"
        else -> "$match% match"
    }
