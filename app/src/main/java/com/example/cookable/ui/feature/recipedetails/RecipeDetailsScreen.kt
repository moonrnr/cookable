package com.example.cookable.ui.feature.recipedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.repository.FavoritesRecipesRepository
import com.example.cookable.ui.components.Chip
import com.example.cookable.ui.components.MatchBadge
import com.example.cookable.ui.components.StatusBox
import com.example.cookable.ui.components.TotalTimeBadge
import com.example.cookable.ui.theme.Background
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.White

@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    navController: NavController,
    favoritesRepository: FavoritesRecipesRepository,
) {
    val scrollState = rememberScrollState()

    val isFavorite by favoritesRepository
        .isFavorite(recipe.id)
        .collectAsState(false)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Background),
    ) {
        Box {
            AsyncImage(
                model = recipe.imgSrc,
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier =
                        Modifier
                            .background(
                                color = Black.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(50),
                            ),
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }

                IconButton(
                    onClick = { favoritesRepository.toggleFavorite(recipe) },
                    modifier =
                        Modifier
                            .background(
                                color = Black.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(50),
                            ),
                ) {
                    Icon(
                        imageVector =
                            if (isFavorite) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Outlined.FavoriteBorder
                            },
                        contentDescription = "Add to favorites",
                        tint = White,
                    )
                }
            }

            Row(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MatchBadge(
                    matchPercentage = recipe.matchPercentage,
                    level = recipe.matchLevel,
                )
                TotalTimeBadge(totalTime = recipe.totalTime)
            }
        }

        Column(
            modifier =
                Modifier
                    .verticalScroll(scrollState)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = recipe.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                recipe.tags.forEach { tag ->
                    Chip(text = tag)
                }
            }

            Divider(color = Line)

            if (recipe.hasAllIngredients) {
                StatusBox(
                    background = Color(0xFF2E7D32).copy(alpha = 0.12f),
                    textColor = Color(0xFF2E7D32),
                    text = "✓ You have all ingredients",
                )
            } else {
                val missingText =
                    if (recipe.missingIngredients.size > 4) {
                        recipe.missingIngredients.take(4).joinToString(", ") + ", and more..."
                    } else {
                        recipe.missingIngredients.joinToString(", ")
                    }

                StatusBox(
                    background = Color(0xFFC65A00).copy(alpha = 0.14f),
                    textColor = Color(0xFFC65A00),
                    text = "⚠ Missing: $missingText",
                )
            }

            Divider(color = Line)

            SectionTitle("Ingredients")
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                recipe.ingredients.forEach { ingredient ->
                    Text(
                        text = "• ${ingredient.name}",
                        fontSize = 14.sp,
                    )
                }
            }

            Divider(color = Line)

            SectionTitle("Directions")
            Text(
                text = recipe.directions,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )
}
