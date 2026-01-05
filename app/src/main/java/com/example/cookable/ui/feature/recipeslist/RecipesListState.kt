package com.example.cookable.ui.feature.recipeslist

import com.example.cookable.domain.model.Recipe

data class RecipesListState(
    val isLoading: Boolean = true,
    val recipes: List<Recipe> = emptyList(),
)
