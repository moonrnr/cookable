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
        isVegetarian = isVegetarian,
        isVegan = isVegan,
        isGlutenFree = isGlutenFree,
        isKeto = isKeto,
        isBreakfast = isKeto,
        isLunch = isLunch,
        isDinner = isDinner,
        isSupper = isSupper,
        preparationDifficulty = preparationDifficulty,
        timeMinutes = timeMinutes,
    )
