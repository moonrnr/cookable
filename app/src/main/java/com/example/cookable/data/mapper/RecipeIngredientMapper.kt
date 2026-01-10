package com.example.cookable.data.mapper

import com.example.cookable.data.remote.dto.RecipeIngredientDto
import com.example.cookable.domain.model.RecipeIngredient

fun RecipeIngredientDto.toDomain(): RecipeIngredient =
    RecipeIngredient(
        id = id,
        ingredientName = ingredient_name,
        ingredientNameCode = ingredient_name_code,
        displayName = display_name,
    )
