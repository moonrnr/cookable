package com.example.cookable.ui.components.missingingredientsbox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cookable.domain.model.Status
import com.example.cookable.ui.components.statusbox.StatusBox

@Composable
fun MissingIngredientsBox(
    hasAllIngredients: Boolean,
    missingIngredients: List<String>,
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
            if (missingIngredients.size > 4) {
                missingIngredients
                    .take(3)
                    .joinToString(", ") + ", and more..."
            } else {
                missingIngredients.joinToString(", ")
            }

        StatusBox(
            status = Status.WARNING,
            text = "Missing: $missingText",
            modifier = modifier,
        )
    }
}
