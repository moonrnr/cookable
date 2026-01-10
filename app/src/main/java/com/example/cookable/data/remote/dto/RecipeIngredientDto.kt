package com.example.cookable.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeIngredientDto(
    val id: Int,
    val ingredient_name: String,
    val ingredient_name_code: String,
    val display_name: String,
)
