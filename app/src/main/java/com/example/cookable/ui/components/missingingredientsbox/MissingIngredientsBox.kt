package com.example.cookable.ui.components.missingingredientsbox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cookable.domain.model.RecipeIngredient
import com.example.cookable.domain.model.Status
import com.example.cookable.ui.components.statusbox.StatusBox

@Composable
fun MissingIngredientsBox(
    hasAllIngredients: Boolean,
    missingIngredients: List<RecipeIngredient>,
    modifier: Modifier = Modifier,
) {
    if (hasAllIngredients) {
        StatusBox(
            status = Status.SUCCESS,
            text = "All ingredients available",
            modifier = modifier,
        )
    } else {
        val missingText =
            if (missingIngredients.size > 3) {
                missingIngredients
                    .take(3)
                    .joinToString(", ") { it.ingredientName }
                    .plus(", and more...")
            } else {
                missingIngredients.joinToString(", ") { it.ingredientName }
            }

        StatusBox(
            status = Status.WARNING,
            text = "Missing: $missingText",
            modifier = modifier,
        )
    }
}
