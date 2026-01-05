package com.example.cookable.domain.repository

import com.example.cookable.data.mapper.toDomain
import com.example.cookable.data.remote.api.RecipesMockApi
import com.example.cookable.domain.model.Recipe

class RecipesRepositoryImpl(
    private val api: RecipesMockApi,
) : RecipesRepository {
    override suspend fun getRecipes(): List<Recipe> = api.getRecipes().map { it.toDomain() }
}
