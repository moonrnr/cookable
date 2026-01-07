package com.example.cookable.ui.feature.recipeslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookable.domain.model.MatchLevel
import com.example.cookable.domain.model.PreparationDifficulty
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.model.SortOption
import com.example.cookable.domain.repository.RecipesRepositoryProvider
import com.example.cookable.ui.components.filterbottomsheet.FilterBottomSheetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipesListViewModel : ViewModel() {
    private val repository =
        RecipesRepositoryProvider.instance

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

            _state.value =
                _state.value.copy(
                    isLoading = false,
                    recipes =
                        applyFiltersAndSort(
                            allRecipes,
                            _state.value.filters,
                            _state.value.sortOption,
                        ),
                )
        }
    }

    private fun applyFiltersAndSort(
        recipes: List<Recipe>,
        filters: FilterBottomSheetState,
        sort: SortOption,
    ): List<Recipe> {
        var result = recipes

        if (filters.vegetarian) result = result.filter { it.isVegetarian }
        if (filters.vegan) result = result.filter { it.isVegan }
        if (filters.glutenFree) result = result.filter { it.isGlutenFree }
        if (filters.keto) result = result.filter { it.isKeto }

        if (filters.breakfast) result = result.filter { it.isBreakfast }
        if (filters.lunch) result = result.filter { it.isLunch }
        if (filters.dinner) result = result.filter { it.isDinner }
        if (filters.supper) result = result.filter { it.isSupper }

        val difficulties =
            buildSet {
                if (filters.difficultyEasy) add(PreparationDifficulty.EASY)
                if (filters.difficultyMedium) add(PreparationDifficulty.MEDIUM)
                if (filters.difficultyHard) add(PreparationDifficulty.HARD)
            }

        if (difficulties.isNotEmpty()) {
            result = result.filter { it.preparationDifficulty in difficulties }
        }

        filters.minTime.toIntOrNull()?.let { min ->
            result = result.filter { it.timeMinutes >= min }
        }

        filters.maxTime.toIntOrNull()?.let { max ->
            result = result.filter { it.timeMinutes <= max }
        }

        val matchLevels =
            buildSet {
                if (filters.matchPerfect) add(MatchLevel.PERFECT)
                if (filters.matchHigh) add(MatchLevel.HIGH)
                if (filters.matchMedium) add(MatchLevel.MEDIUM)
                if (filters.matchLow) add(MatchLevel.LOW)
            }

        if (matchLevels.isNotEmpty()) {
            result = result.filter { it.matchLevel in matchLevels }
        }

        return when (sort) {
            SortOption.MATCH ->
                result.sortedByDescending { it.matchPercentage }

            SortOption.ALPHABETICAL ->
                result.sortedBy { it.name.lowercase() }
        }
    }

    fun setSortOption(option: SortOption) {
        _state.value =
            _state.value.copy(
                sortOption = option,
                recipes =
                    applyFiltersAndSort(
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
                    applyFiltersAndSort(
                        allRecipes,
                        filters,
                        _state.value.sortOption,
                    ),
            )
    }
}
