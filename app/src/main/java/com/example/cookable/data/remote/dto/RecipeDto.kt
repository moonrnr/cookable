package com.example.cookable.data.remote.dto

import com.example.cookable.domain.model.MatchLevel
import com.example.cookable.domain.model.PreparationDifficulty
import com.example.cookable.domain.model.SectionType
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val sectionType: SectionType,
    val recipe_name: String,
    val total_time: String,
    val ingredients: List<RecipeIngredientDto>,
    val directions: String,
    val cuisine_path: String,
    val img_src: String,
    val match_percentage: Int,
    val match_level: MatchLevel,
    val missing_ingredients: List<RecipeIngredientDto>,
    val missing_ingredients_count: Int,
    val has_all_ingredients: Boolean,
    val tags: List<String>,
    val is_vegetarian: Boolean,
    val is_vegan: Boolean,
    val is_gluten_free: Boolean,
    val is_keto: Boolean,
    val is_breakfast: Boolean,
    val is_lunch: Boolean,
    val is_dinner: Boolean,
    val is_supper: Boolean,
    val preparation_difficulty: PreparationDifficulty,
    val time_minutes: Int,
)
