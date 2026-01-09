package com.example.cookable.domain.usecase

import com.example.cookable.domain.model.MatchLevel
import com.example.cookable.domain.model.PreparationDifficulty
import com.example.cookable.domain.model.Recipe
import com.example.cookable.domain.model.SortOption
import com.example.cookable.ui.components.filterbottomsheet.FilterBottomSheetState

class FilterAndSortRecipesUseCase {
    private fun sortRecipesList(
        result: List<Recipe>,
        sort: SortOption,
    ): List<Recipe> =
        when (sort) {
            SortOption.MATCH ->
                result.sortedByDescending { it.matchPercentage }

            SortOption.ALPHABETICAL_ASC ->
                result.sortedBy { it.name.lowercase() }

            SortOption.TIME_ASC ->
                result.sortedBy { it.timeMinutes }
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

        return sortRecipesList(result, sort)
    }

    operator fun invoke(
        recipes: List<Recipe>,
        filters: FilterBottomSheetState,
        sort: SortOption,
    ): List<Recipe> = applyFiltersAndSort(recipes, filters, sort)
}
