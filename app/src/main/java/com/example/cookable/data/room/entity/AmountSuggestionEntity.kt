package com.example.cookable.data.room.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "ingredient_amount_suggestions",
    primaryKeys = ["ingredientName", "amount"],
    indices = [Index("ingredientName")],
)
data class AmountSuggestionEntity(
    val ingredientName: String,
    val amount: Double,
)
