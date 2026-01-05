package com.example.cookable.data.mapper

import com.example.cookable.data.remote.dto.RecipeDto
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.model.Recipe
import java.util.UUID

fun RecipeDto.toDomain(): Recipe =
    Recipe(
        id = UUID.randomUUID().toString(),
        name = recipe_name,
        ingredients =
            ingredients
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { ingredientName ->
                    Ingredient(
                        name = ingredientName,
                        amount = null,
                        unit = null,
                    )
                },
        directions = directions,
        isFavorite = false,
        cuisineCategories = cuisine_path,
        imgSrc = img_src,
        totalTime = total_time,
        matchPercentage = match_percentage,
        matchLevel = match_level,
        missingIngredients = missing_ingredients,
        missingIngredientsCount = missing_ingredients_count,
        hasAllIngredients = has_all_ingredients,
        tags = tags,
    )
