package com.example.cookable.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "frequent_ingredients")
data class IngredientEntity(
    @PrimaryKey val name: String,
)
