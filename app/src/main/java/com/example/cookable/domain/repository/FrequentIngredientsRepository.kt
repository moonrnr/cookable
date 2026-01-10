package com.example.cookable.domain.repository

import com.example.cookable.domain.model.FrequentIngredient
import com.example.cookable.domain.model.UnitType

interface FrequentIngredientsRepository {
    suspend fun searchByName(query: String): List<String>

    suspend fun getFrequentIngredient(name: String): FrequentIngredient?

    suspend fun saveIngredientVariant(
        name: String,
        amount: Double?,
        unit: UnitType?,
    )
}
