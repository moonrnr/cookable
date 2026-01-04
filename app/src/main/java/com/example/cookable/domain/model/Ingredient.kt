package com.example.cookable.domain.model

data class Ingredient(
    val name: String,
    val amount: Double?,
    val unit: UnitType?,
    val amountSuggestion: Double? = null,
    val unitSuggestion: UnitType? = null,
    val isRecognized: Boolean = false,
    val hasError: Boolean = false
) {
    companion object {
        fun empty() = Ingredient(
            name = "",
            amount = null,
            unit = null
        )
    }
}
