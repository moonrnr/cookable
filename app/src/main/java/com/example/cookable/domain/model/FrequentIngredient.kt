package com.example.cookable.domain.model

data class FrequentIngredient(
    val name: String,
    val suggestedAmounts: List<Double>,
    val suggestedUnits: List<UnitType>,
)
