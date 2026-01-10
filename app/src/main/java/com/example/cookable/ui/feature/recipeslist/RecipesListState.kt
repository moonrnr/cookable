package com.example.cookable.ui.feature.recipeslist

import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.model.SortOption
import com.example.cookable.ui.components.filterbottomsheet.FilterBottomSheetState

data class RecipesListState(
    val isLoading: Boolean = true,
    val recipes: List<Recipe> = emptyList(),
    val sortOption: SortOption = SortOption.MATCH,
    val filters: FilterBottomSheetState = FilterBottomSheetState(),
    val availableTags: List<String> = emptyList(),
)
