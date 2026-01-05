package com.example.cookable.ui.feature.recipedetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.repository.FavoritesRecipesRepository
import com.example.cookable.domain.repository.FavoritesRecipesRepositoryImpl
import kotlinx.coroutines.flow.Flow

class RecipeDetailsViewModel(
    app: Application,
) : AndroidViewModel(app) {
    private val favoritesRepository: FavoritesRecipesRepository =
        FavoritesRecipesRepositoryImpl()

    fun isFavorite(recipeId: String): Flow<Boolean> = favoritesRepository.isFavorite(recipeId)

    fun toggleFavorite(recipe: Recipe) {
        favoritesRepository.toggleFavorite(recipe)
    }
}
