package com.example.cookable.core.util


import androidx.compose.ui.graphics.Color
import com.example.cookable.domain.model.MatchLevel
import com.example.cookable.ui.theme.*

fun transformMatchLevelToTextAndBackgroundColors(level: MatchLevel): Pair<Color, Color> =
    when (level) {
        MatchLevel.PERFECT -> Color(0xFF2E7D32) to White
        MatchLevel.HIGH -> Color(0xFF1c7c54) to White
        MatchLevel.MEDIUM -> Color(0xFFcc9c00)  to White
        MatchLevel.LOW -> Color(0xFF7A1F00) to White
    }
