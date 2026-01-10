package com.example.cookable.data.mapper

import com.example.cookable.data.remote.dto.RecipeIngredientDto
import com.example.cookable.domain.model.RecipeIngredient

fun List<RecipeIngredientDto>.toDomain(): List<RecipeIngredient> = map { it.toDomain() }
