package com.example.cookable.ui.feature.recipedetails

import androidx.lifecycle.ViewModel
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.repository.FavoritesRepositoryProvider
import kotlinx.coroutines.flow.Flow

class RecipeDetailsViewModel : ViewModel() {
    private val favoritesRepository =
        FavoritesRepositoryProvider.instance

    fun isFavorite(recipeId: String): Flow<Boolean> = favoritesRepository.isFavorite(recipeId)

    fun toggleFavorite(recipe: Recipe) {
        favoritesRepository.toggleFavorite(recipe)
    }
}
