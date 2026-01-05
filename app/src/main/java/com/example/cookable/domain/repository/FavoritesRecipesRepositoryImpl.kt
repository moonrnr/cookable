package com.example.cookable.domain.repository

import com.example.cookable.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class FavoritesRecipesRepositoryImpl : FavoritesRecipesRepository {
    private val _favorites = MutableStateFlow<List<Recipe>>(emptyList())
    override val favorites: StateFlow<List<Recipe>> = _favorites

    override fun addToFavorites(recipe: Recipe) {
        if (_favorites.value.none { it.id == recipe.id }) {
            _favorites.value = _favorites.value + recipe.copy(isFavorite = true)
        }
    }

    override fun removeFromFavorites(recipeId: String) {
        _favorites.value = _favorites.value.filterNot { it.id == recipeId }
    }

    override fun toggleFavorite(recipe: Recipe) {
        val exists = _favorites.value.any { it.id == recipe.id }
        if (exists) {
            removeFromFavorites(recipe.id)
        } else {
            addToFavorites(recipe)
        }
    }

    override fun isFavorite(recipeId: String): Flow<Boolean> =
        favorites.map { list ->
            list.any { it.id == recipeId }
        }
}
