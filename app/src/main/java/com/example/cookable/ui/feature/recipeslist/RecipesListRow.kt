package com.example.cookable.ui.feature.recipeslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.cookable.domain.model.Recipe
import com.example.cookable.ui.components.Chip
import com.example.cookable.ui.components.MatchBadge
import com.example.cookable.ui.components.StatusBox
import com.example.cookable.ui.components.TotalTimeBadge
import com.example.cookable.ui.theme.Card
import com.example.cookable.ui.theme.Line

@Composable
fun RecipesListItemRow(
    recipe: Recipe,
    recipesListType: RecipesListType,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
) {
    val shape = RoundedCornerShape(18.dp)

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .shadow(10.dp, shape)
                .background(Card, shape)
                .clickable(onClick = onClick),
    ) {
        Box {
            if (recipe.imgSrc.isNotBlank()) {
                AsyncImage(
                    model = recipe.imgSrc,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)),
                )
            } else {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(
                                Line,
                                RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
                            ),
                )
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                verticalAlignment = Alignment.Top,
            ) {
                if (recipesListType == RecipesListType.ALL_RECIPES) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        MatchBadge(
                            matchPercentage = recipe.matchPercentage,
                            level = recipe.matchLevel,
                        )
                        TotalTimeBadge(totalTime = recipe.totalTime)
                    }
                }

                Spacer(Modifier.weight(1f))

                IconButton(
                    onClick = onToggleFavorite,
                    modifier =
                        Modifier
                            .background(
                                color = Black.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(50),
                            ),
                ) {
                    Icon(
                        imageVector =
                            if (recipe.isFavorite) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Outlined.FavoriteBorder
                            },
                        contentDescription = "Add to favorites",
                        tint = White,
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = recipe.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                recipe.tags.take(3).forEach { tag ->
                    Chip(text = tag)
                }
            }

            if (recipesListType == RecipesListType.ALL_RECIPES) {
                Divider(color = Line)

                if (recipe.hasAllIngredients) {
                    StatusBox(
                        background = Color(0xFF2E7D32).copy(alpha = 0.12f),
                        textColor = Color(0xFF2E7D32),
                        text = "✓ All ingredients available",
                    )
                } else {
                    val missingText =
                        if (recipe.missingIngredients.size > 4) {
                            recipe.missingIngredients
                                .take(3)
                                .joinToString(", ") + ", and more..."
                        } else {
                            recipe.missingIngredients.joinToString(", ")
                        }

                    StatusBox(
                        background = Color(0xFFF57C00).copy(alpha = 0.14f),
                        textColor = Color(0xFFF57C00),
                        text = "⚠ Missing: $missingText",
                    )
                }
            }
        }
    }
}
