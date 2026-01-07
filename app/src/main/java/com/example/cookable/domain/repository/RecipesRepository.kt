package com.example.cookable.domain.repository

import com.example.cookable.domain.model.Recipe
import kotlinx.coroutines.flow.StateFlow

interface RecipesRepository {
    val recipes: StateFlow<List<Recipe>>

    suspend fun getRecipes(): List<Recipe>
}
