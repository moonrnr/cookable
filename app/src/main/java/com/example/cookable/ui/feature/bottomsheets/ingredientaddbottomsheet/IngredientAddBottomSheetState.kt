package com.example.cookable.ui.feature.bottomsheets.ingredientaddbottomsheet

import com.example.cookable.domain.model.UnitType

data class IngredientAddState(
    val name: String = "",
    val amount: String = "",
    val unit: UnitType? = null,
    val nameSuggestions: List<String> = emptyList(),
    val suggestedAmounts: List<Double> = emptyList(),
    val suggestedUnits: List<UnitType> = emptyList(),
    val isSaveEnabled: Boolean = false,
    val deleteSuggestion: DeleteSuggestion? = null,
)

sealed class DeleteSuggestion {
    data class Amount(
        val value: Double,
    ) : DeleteSuggestion()

    data class Unit(
        val value: UnitType,
    ) : DeleteSuggestion()

    data class Ingredient(
        val name: String,
    ) : DeleteSuggestion()
}
