package com.example.cookable.ui.components.ingredientscountbadge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IngredientsCountBadge(
    ingredientsCount: Int,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    isTextBold: Boolean = false,
    fontSize: TextUnit = 12.sp,
) {
    Box(
        modifier =
            modifier
                .background(backgroundColor, RoundedCornerShape(999.dp))
                .padding(horizontal = 10.dp, vertical = 2.dp),
    ) {
        Text(
            text = ingredientsCount.toString(),
            fontSize = fontSize,
            color = textColor,
            fontWeight = if (isTextBold) FontWeight.Bold else FontWeight.Normal,
        )
    }
}
