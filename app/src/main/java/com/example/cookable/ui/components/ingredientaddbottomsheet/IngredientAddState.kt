package com.example.cookable.ui.components.ingredientaddbottomsheet

import com.example.cookable.domain.model.UnitType

data class IngredientAddState(
    val name: String = "",
    val amount: String = "",
    val unit: UnitType? = null,
    val nameSuggestions: List<String> = emptyList(),
    val suggestedAmounts: List<Double> = emptyList(),
    val suggestedUnits: List<UnitType> = emptyList(),
    val isSaveEnabled: Boolean = false,
)
