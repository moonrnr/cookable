package com.example.cookable.ui.feature.recipedetails.components.ingredients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.domain.model.RecipeIngredient
import com.example.cookable.ui.theme.PrimaryOrange

@Composable
fun IngredientsList(
    ingredients: List<RecipeIngredient>,
    missingIngredients: List<RecipeIngredient>,
) {
    val missingCodes =
        remember(missingIngredients) {
            missingIngredients.map { it.ingredientNameCode }.toSet()
        }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        ingredients.forEach { ingredient ->
            val isMissing = ingredient.ingredientNameCode in missingCodes

            Text(
                text = "• ${ingredient.displayName}",
                fontSize = 14.sp,
                color = if (isMissing) PrimaryOrange else Color.Unspecified,
                modifier =
                    Modifier
                        .then(
                            if (isMissing) {
                                Modifier
                                    .background(
                                        color = PrimaryOrange.copy(alpha = 0.14f),
                                        shape = RoundedCornerShape(14.dp),
                                    ).padding(horizontal = 6.dp, vertical = 2.dp)
                            } else {
                                Modifier
                            },
                        ),
            )
        }
    }
}
