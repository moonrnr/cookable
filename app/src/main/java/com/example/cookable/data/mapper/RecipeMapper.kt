package com.example.cookable.data.mapper

import com.example.cookable.data.remote.dto.RecipeDto
import com.example.cookable.domain.model.Recipe

fun RecipeDto.toDomain(): Recipe =
    Recipe(
        id = id.toString(),
        sectionType = sectionType,
        name = recipe_name,
        ingredients = ingredients.toDomain(),
        missingIngredients = missing_ingredients.toDomain(),
        directions = directions,
        isFavorite = false,
        cuisineCategories = cuisine_path,
        imgSrc = img_src,
        totalTime = total_time,
        matchPercentage = match_percentage,
        matchLevel = match_level,
        missingIngredientsCount = missing_ingredients_count,
        hasAllIngredients = has_all_ingredients,
        tags = tags,
        isVegetarian = is_vegetarian,
        isVegan = is_vegan,
        isGlutenFree = is_gluten_free,
        isKeto = is_keto,
        isBreakfast = is_breakfast,
        isLunch = is_lunch,
        isDinner = is_dinner,
        isSupper = is_supper,
        preparationDifficulty = preparation_difficulty,
        timeMinutes = time_minutes,
    )
