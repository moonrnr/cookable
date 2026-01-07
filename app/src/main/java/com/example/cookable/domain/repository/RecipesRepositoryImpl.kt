package com.example.cookable.domain.repository

import com.example.cookable.data.mapper.toDomain
import com.example.cookable.data.remote.api.RecipesMockApi
import com.example.cookable.domain.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipesRepositoryImpl(
    private val api: RecipesMockApi,
) : RecipesRepository {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    override val recipes: StateFlow<List<Recipe>> = _recipes

    override suspend fun getRecipes(): List<Recipe> {
        val list = api.getRecipes().map { it.toDomain() }
        _recipes.value = list
        return list
    }
}
