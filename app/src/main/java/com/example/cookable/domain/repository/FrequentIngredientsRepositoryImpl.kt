package com.example.cookable.domain.repository

import com.example.cookable.data.room.dao.FrequentIngredientsDao
import com.example.cookable.data.room.entity.AmountSuggestionEntity
import com.example.cookable.data.room.entity.IngredientEntity
import com.example.cookable.data.room.entity.UnitSuggestionEntity
import com.example.cookable.domain.model.FrequentIngredient
import com.example.cookable.domain.model.UnitType

class FrequentIngredientsRepositoryImpl(
    private val dao: FrequentIngredientsDao,
) : FrequentIngredientsRepository {
    override suspend fun searchByName(query: String): List<String> {
        if (query.isBlank()) return emptyList()
        return dao.searchByName(query.trim())
    }

    override suspend fun getFrequentIngredient(name: String): FrequentIngredient? {
        val normalizedName = name.trim()
        if (!dao.ingredientExists(normalizedName)) return null

        return FrequentIngredient(
            name = normalizedName,
            suggestedAmounts = dao.getAmounts(normalizedName),
            suggestedUnits = dao.getUnits(normalizedName),
        )
    }

    override suspend fun saveIngredientVariant(
        name: String,
        amount: Double?,
        unit: UnitType?,
    ) {
        val normalizedName = name.trim()
        if (normalizedName.isBlank()) return

        dao.insertIngredient(
            IngredientEntity(name = normalizedName),
        )

        amount?.let {
            dao.insertAmountSuggestion(
                AmountSuggestionEntity(
                    ingredientName = normalizedName,
                    amount = it,
                ),
            )
        }

        unit?.let {
            dao.insertUnitSuggestion(
                UnitSuggestionEntity(
                    ingredientName = normalizedName,
                    unit = it,
                ),
            )
        }
    }

    override suspend fun removeSuggestedAmount(
        name: String,
        amount: Double,
    ) {
        val normalizedName = name.trim()
        if (normalizedName.isBlank()) return

        dao.deleteAmountSuggestion(
            name = normalizedName,
            amount = amount,
        )
    }

    override suspend fun removeSuggestedUnit(
        name: String,
        unit: UnitType,
    ) {
        val normalizedName = name.trim()
        if (normalizedName.isBlank()) return

        dao.deleteUnitSuggestion(
            name = normalizedName,
            unit = unit,
        )
    }

    override suspend fun removeIngredient(name: String) {
        val normalizedName = name.trim()
        if (normalizedName.isBlank()) return

        dao.deleteIngredient(normalizedName)
    }
}
