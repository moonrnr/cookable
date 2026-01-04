package com.example.cookable.data.remote.api
import android.content.Context
import com.example.cookable.data.remote.dto.RecipeDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class RecipesMockApi(
    private val context: Context
) {

    suspend fun getRecipes(): List<RecipeDto> {
        delay(1500)

        val json = context.assets
            .open("recipes.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<RecipeDto>>() {}.type
        return Gson().fromJson(json, type)
    }
}
