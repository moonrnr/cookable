package com.example.cookable.ui.feature.recipeslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.model.SortOption
import com.example.cookable.domain.repository.FavoritesRepositoryProvider
import com.example.cookable.domain.repository.RecipesRepositoryProvider
import com.example.cookable.domain.usecase.FilterAndSortRecipesUseCase
import com.example.cookable.ui.components.filterbottomsheet.FilterBottomSheetState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipesListViewModel : ViewModel() {
    private val repository =
        RecipesRepositoryProvider.instance

    private val favoritesRepository =
        FavoritesRepositoryProvider.instance

    private val filterAndSortRecipes =
        FilterAndSortRecipesUseCase()
    private val _state = MutableStateFlow(RecipesListState())
    val state = _state.asStateFlow()

    private var allRecipes: List<Recipe> = emptyList()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            allRecipes = repository.getRecipes()

            val allTags =
                allRecipes
                    .flatMap { it.tags }
                    .distinct()
                    .sorted()

            _state.value =
                _state.value.copy(
                    isLoading = false,
                    availableTags = allTags,
                    recipes =
                        filterAndSortRecipes(
                            allRecipes,
                            _state.value.filters,
                            _state.value.sortOption,
                        ),
                )
        }
    }

    fun setSortOption(option: SortOption) {
        _state.value =
            _state.value.copy(
                sortOption = option,
                recipes =
                    filterAndSortRecipes(
                        allRecipes,
                        _state.value.filters,
                        option,
                    ),
            )
    }

    fun setFilters(filters: FilterBottomSheetState) {
        _state.value =
            _state.value.copy(
                filters = filters,
                recipes =
                    filterAndSortRecipes(
                        allRecipes,
                        filters,
                        _state.value.sortOption,
                    ),
            )
    }

    fun isFavorite(recipeId: String): Flow<Boolean> = favoritesRepository.isFavorite(recipeId)

    fun toggleFavorite(recipe: Recipe) {
        favoritesRepository.toggleFavorite(recipe)
    }
}
