package com.example.cookable.ui.feature.favorites

import androidx.lifecycle.ViewModel
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.repository.FavoritesRepositoryProvider
import kotlinx.coroutines.flow.StateFlow

class FavoriteRecipesListViewModel : ViewModel() {
    private val repository =
        FavoritesRepositoryProvider.instance

    val favorites: StateFlow<List<Recipe>> =
        repository.favorites

    fun toggleFavorite(recipe: Recipe) {
        repository.toggleFavorite(recipe)
    }
}
