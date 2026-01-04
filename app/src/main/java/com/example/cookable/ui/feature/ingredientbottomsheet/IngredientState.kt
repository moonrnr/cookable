package com.example.cookable.ui.feature.ingredientbottomsheet

import com.example.cookable.domain.model.UnitType

data class IngredientState(
    val name: String = "",
    val amount: String = "",
    val unit: UnitType? = null,
    val amountSuggestion: String? = null,
    val unitSuggestion: UnitType? = null
) {
    val canSave: Boolean
        get() = name.isNotBlank() && amount.isNotBlank() && unit != null
}
