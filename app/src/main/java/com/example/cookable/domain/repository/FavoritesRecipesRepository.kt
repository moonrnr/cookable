package com.example.cookable.domain.repository

import com.example.cookable.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FavoritesRecipesRepository {
    val favorites: StateFlow<List<Recipe>>

    fun addToFavorites(recipe: Recipe)

    fun removeFromFavorites(recipeId: String)

    fun toggleFavorite(recipe: Recipe)

    fun isFavorite(recipeId: String): Flow<Boolean>
}
