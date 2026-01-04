package com.example.cookable.domain.model

data class Recipe(
    val id: String,
    val name: String,
    val ingredients: List<Ingredient>,
    val directions: String,
    val isFavorite: Boolean = false,
    val cuisineCategories: String,
    val imgSrc: String,
    val totalTime: String,
    val matchPercentage: Int,
    val matchLevel: MatchLevel,
    val missingIngredients: List<String>,
    val missingIngredientsCount: Int,
    val hasAllIngredients: Boolean,
    val tags: List<String>,
)
