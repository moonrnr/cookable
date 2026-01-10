package com.example.cookable.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cookable.data.room.entity.AmountSuggestionEntity
import com.example.cookable.data.room.entity.IngredientEntity
import com.example.cookable.data.room.entity.UnitSuggestionEntity
import com.example.cookable.domain.model.UnitType

@Dao
interface FrequentIngredientsDao {
    @Query(
        """
        SELECT name FROM frequent_ingredients
        WHERE name LIKE :query || '%'
        ORDER BY name ASC
    """,
    )
    suspend fun searchByName(query: String): List<String>

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertIngredient(entity: IngredientEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertAmountSuggestion(entity: AmountSuggestionEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertUnitSuggestion(entity: UnitSuggestionEntity)

    @Query(
        """
        SELECT amount FROM ingredient_amount_suggestions
        WHERE ingredientName = :name
        ORDER BY amount ASC
    """,
    )
    suspend fun getAmounts(name: String): List<Double>

    @Query(
        """
        SELECT unit FROM ingredient_unit_suggestions
        WHERE ingredientName = :name
    """,
    )
    suspend fun getUnits(name: String): List<UnitType>

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM frequent_ingredients WHERE name = :name
        )
    """,
    )
    suspend fun ingredientExists(name: String): Boolean

    @Transaction
    suspend fun deleteIngredient(name: String) {
        deleteAmountSuggestionsForIngredient(name)
        deleteUnitSuggestionsForIngredient(name)
        deleteIngredientEntity(name)
    }

    @Query("DELETE FROM ingredient_amount_suggestions WHERE ingredientName = :name")
    suspend fun deleteAmountSuggestionsForIngredient(name: String)

    @Query("DELETE FROM ingredient_unit_suggestions WHERE ingredientName = :name")
    suspend fun deleteUnitSuggestionsForIngredient(name: String)

    @Query("DELETE FROM frequent_ingredients WHERE name = :name")
    suspend fun deleteIngredientEntity(name: String)

    @Query(
        """
    DELETE FROM ingredient_amount_suggestions
    WHERE ingredientName = :name AND amount = :amount
""",
    )
    suspend fun deleteAmountSuggestion(
        name: String,
        amount: Double,
    )

    @Query(
        """
    DELETE FROM ingredient_unit_suggestions
    WHERE ingredientName = :name AND unit = :unit
""",
    )
    suspend fun deleteUnitSuggestion(
        name: String,
        unit: UnitType,
    )
}
