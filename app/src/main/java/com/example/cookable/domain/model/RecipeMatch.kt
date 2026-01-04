package com.example.cookable.domain.model

data class RecipeMatch(
    val recipe: Recipe,
    val matchedIngredients: List<Ingredient>,
    val missingIngredients: List<Ingredient>
)
