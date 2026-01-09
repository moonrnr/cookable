package com.example.cookable.domain.repository

import com.example.cookable.data.mapper.toDomain
import com.example.cookable.data.remote.api.RecipesMockApi
import com.example.cookable.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class FavoritesRecipesRepositoryImpl(
    private val api: RecipesMockApi,
) : FavoritesRecipesRepository {
    private var filterByIngredients: Boolean = false

    private val _favorites = MutableStateFlow<List<Recipe>>(emptyList())
    override val favorites: StateFlow<List<Recipe>> = _favorites

    private val _favoritesFromFindRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    override val favoritesFromFindRecipes: StateFlow<List<Recipe>> = _favoritesFromFindRecipes

    private fun isFavoriteInternal(recipeId: String): Boolean =
        _favorites.value.any { it.id == recipeId } ||
            _favoritesFromFindRecipes.value.any { it.id == recipeId }

    override fun addToFavorites(recipe: Recipe) {
        if (_favoritesFromFindRecipes.value.none { it.id == recipe.id }) {
            _favoritesFromFindRecipes.value += recipe.copy(isFavorite = true)
        }
    }

    override fun removeFromFavorites(recipeId: String) {
        _favorites.value = _favorites.value.filterNot { it.id == recipeId }

        if (_favoritesFromFindRecipes.value.isNotEmpty() &&
            _favoritesFromFindRecipes.value.any { it.id == recipeId }
        ) {
            _favoritesFromFindRecipes.value =
                _favoritesFromFindRecipes.value.filterNot { it.id == recipeId }
        }
    }

    override fun toggleFavorite(recipe: Recipe) {
        if (isFavoriteInternal(recipe.id)) {
            removeFromFavorites(recipe.id)
        } else {
            addToFavorites(recipe)
        }
    }

    override fun isFavorite(recipeId: String): Flow<Boolean> =
        combine(
            favorites,
            favoritesFromFindRecipes,
        ) { favorites, fromFind ->
            favorites.any { it.id == recipeId } ||
                fromFind.any { it.id == recipeId }
        }

    override suspend fun getFavoriteRecipes(): List<Recipe> {
        val apiList =
            if (filterByIngredients) {
                api.getFavoriteRecipesFiltered()
            } else {
                api.getFavoriteRecipes()
            }.map { it.toDomain() }

        val fromFind = _favoritesFromFindRecipes.value

        val merged =
            (apiList + fromFind)
                .groupBy { it.id }
                .map { (_, recipes) ->
                    recipes.firstOrNull { it in fromFind } ?: recipes.first()
                }

        _favorites.value = merged
        return merged
    }

    override fun setFilterByIngredients(enabled: Boolean) {
        filterByIngredients = enabled
    }
}
