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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.model.SectionType
import com.example.cookable.ui.components.chip.Chip
import com.example.cookable.ui.components.iconbuttons.favorite.AddToFavoriteIconButton
import com.example.cookable.ui.components.matchbadge.MatchBadge
import com.example.cookable.ui.components.missingingredientsbox.MissingIngredientsBox
import com.example.cookable.ui.components.totaltimebadge.TotalTimeBadge
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

                AddToFavoriteIconButton(isFavorite = recipe.isFavorite, isSmall = true, onClick = onToggleFavorite)
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
            if (recipe.sectionType == SectionType.RECIPES_LIST) {
                MissingIngredientsBox(
                    hasAllIngredients = recipe.hasAllIngredients,
                    missingIngredients = recipe.missingIngredients,
                )
            }
        }
    }
}
