package com.example.cookable.ui.feature.favorites

import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.model.SortOption
import com.example.cookable.ui.feature.bottomsheets.filterbottomsheet.FilterBottomSheetState

data class FavoriteRecipesListState(
    val recipes: List<Recipe> = emptyList(),
    val filters: FilterBottomSheetState = FilterBottomSheetState(),
    val sortOption: SortOption = SortOption.MATCH,
    val isLoading: Boolean = false,
    val availableTags: List<String> = emptyList(),
)
