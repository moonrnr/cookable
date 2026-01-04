package com.example.cookable.ui.feature.recipedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.cookable.domain.model.Recipe
import com.example.cookable.ui.components.*
import com.example.cookable.ui.theme.*

@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        Box {
            AsyncImage(
                model = recipe.imgSrc,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(50))
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MatchBadge(
                    matchPercentage = recipe.matchPercentage,
                    level = recipe.matchLevel
                )
                TotalTimeBadge(totalTime = recipe.totalTime)
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = recipe.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                    text = "✓ You have all ingredients"
                )
            } else {
                val missingText = if (recipe.missingIngredients.size > 4) {
                    recipe.missingIngredients.take(4).joinToString(", ") + ", and more..."
                } else {
                    recipe.missingIngredients.joinToString(", ")
                }

                StatusBox(
                    background = Color(0xFFC65A00).copy(alpha = 0.14f),
                    textColor = Color(0xFFC65A00),
                    text = "⚠ Missing: $missingText"
                )
            }

            Divider(color = Line)

            SectionTitle("Ingredients")
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                recipe.ingredients.forEach { ingredient ->
                    Text(
                        text = "• ${ingredient.name}",
                        fontSize = 14.sp
                    )
                }
            }

            Divider(color = Line)

            SectionTitle("Directions")
            Text(
                text = recipe.directions,
                fontSize = 14.sp,
                lineHeight = 20.sp
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
        fontWeight = FontWeight.Bold
    )
}
