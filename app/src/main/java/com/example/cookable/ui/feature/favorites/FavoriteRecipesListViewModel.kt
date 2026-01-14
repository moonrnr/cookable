package com.example.cookable.ui.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.model.SortOption
import com.example.cookable.domain.repository.FavoritesRepositoryProvider
import com.example.cookable.domain.usecase.FilterAndSortRecipesUseCase
import com.example.cookable.ui.feature.bottomsheets.filterbottomsheet.FilterBottomSheetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteRecipesListViewModel : ViewModel() {
    private val repository =
        FavoritesRepositoryProvider.instance

    private val filterAndSortRecipes =
        FilterAndSortRecipesUseCase()
    private val _state = MutableStateFlow(FavoriteRecipesListState())
    val state = _state.asStateFlow()

    private var allFavorites: List<Recipe> = emptyList()

    val favorites: StateFlow<List<Recipe>> =
        repository.favorites

    init {
        loadFavoriteRecipes()
    }

    private fun loadFavoriteRecipes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            allFavorites = repository.getFavoriteRecipes()

            val allTags =
                allFavorites
                    .flatMap { it.tags }
                    .distinct()
                    .sorted()

            _state.value =
                _state.value.copy(
                    isLoading = false,
                    availableTags = allTags,
                    recipes =
                        filterAndSortRecipes(
                            allFavorites,
                            _state.value.filters,
                            _state.value.sortOption,
                        ),
                )
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        allFavorites = allFavorites.filterNot { it.id == recipe.id }

        _state.value =
            _state.value.copy(
                recipes =
                    filterAndSortRecipes(
                        allFavorites,
                        _state.value.filters,
                        _state.value.sortOption,
                    ),
            )

        repository.toggleFavorite(recipe)
    }

    fun setSortOption(option: SortOption) {
        _state.value =
            _state.value.copy(
                sortOption = option,
                recipes =
                    filterAndSortRecipes(
                        allFavorites,
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
                        allFavorites,
                        filters,
                        _state.value.sortOption,
                    ),
            )
    }
}
