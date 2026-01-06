package com.example.cookable.data.remote.dto

import com.example.cookable.domain.model.MatchLevel
import com.example.cookable.domain.model.PreparationDifficulty
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val recipe_name: String,
    val total_time: String,
    val ingredients: String,
    val directions: String,
    val cuisine_path: String,
    val img_src: String,
    val match_percentage: Int,
    val match_level: MatchLevel,
    val missing_ingredients: List<String>,
    val missing_ingredients_count: Int,
    val has_all_ingredients: Boolean,
    val tags: List<String>,
    val isVegetarian: Boolean,
    val isVegan: Boolean,
    val isGlutenFree: Boolean,
    val isKeto: Boolean,
    val isBreakfast: Boolean,
    val isLunch: Boolean,
    val isDinner: Boolean,
    val isSupper: Boolean,
    val preparationDifficulty: PreparationDifficulty,
    val timeMinutes: Int,
)
