package com.example.cookable.domain.repository

import com.example.cookable.domain.model.Recipe

interface RecipesRepository {
    suspend fun getRecipes(): List<Recipe>
}
