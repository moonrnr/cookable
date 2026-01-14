package com.example.cookable.data.room.entity

import androidx.room.Entity
import androidx.room.Index
import com.example.cookable.domain.model.UnitType

@Entity(
    tableName = "ingredient_unit_suggestions",
    primaryKeys = ["ingredientName", "unit"],
    indices = [Index("ingredientName")],
)
data class UnitSuggestionEntity(
    val ingredientName: String,
    val unit: UnitType,
)
