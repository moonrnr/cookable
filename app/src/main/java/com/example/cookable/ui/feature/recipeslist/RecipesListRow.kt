package com.example.cookable.ui.feature.recipeslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cookable.core.util.transformMatchLevelToTextAndBackgroundColors
import com.example.cookable.domain.model.MatchLevel
import com.example.cookable.domain.model.Recipe
import com.example.cookable.ui.theme.*
import com.example.cookable.ui.components.MatchBadge
import com.example.cookable.ui.components.Chip
import com.example.cookable.ui.components.StatusBox
import com.example.cookable.ui.components.TotalTimeBadge

@Composable
fun RecipesListItemRow(
    recipe: Recipe,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(18.dp)
    val (matchBg, matchText) = transformMatchLevelToTextAndBackgroundColors(recipe.matchLevel)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, shape)
            .background(Card, shape)
            .clickable(onClick = onClick)
    ) {

        Box {
            if (recipe.imgSrc.isNotBlank()) {
                AsyncImage(
                    model = recipe.imgSrc,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(
                            Line,
                            RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
                        )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                MatchBadge(matchPercentage = recipe.matchPercentage, level = recipe.matchLevel)
                TotalTimeBadge(totalTime = recipe.totalTime)
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = recipe.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                recipe.tags.take(3).forEach { tag ->
                    Chip(text = tag)
                }
            }

            Divider(color = Line)

            if (recipe.hasAllIngredients) {
                StatusBox(
                    background = Color(0xFF2E7D32).copy(alpha = 0.12f),
                    textColor = Color(0xFF2E7D32),
                    text = "✓ All ingredients available"
                )
            } else {
                val missingText = if (recipe.missingIngredients.size > 4) {
                    recipe.missingIngredients
                        .take(3)
                        .joinToString(", ") + ", and more..."
                } else {
                    recipe.missingIngredients.joinToString(", ")
                }

                StatusBox(
                    background = Color(0xFFF57C00).copy(alpha = 0.14f),
                    textColor = Color(0xFFF57C00),
                    text = "⚠ Missing: $missingText"
                )
            }
        }
    }
}

