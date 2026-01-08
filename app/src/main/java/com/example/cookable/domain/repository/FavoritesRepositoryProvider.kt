package com.example.cookable.domain.repository

import android.content.Context
import com.example.cookable.data.remote.api.RecipesMockApi

object FavoritesRepositoryProvider {
    private var initialized = false
    lateinit var instance: FavoritesRecipesRepository

    fun init(context: Context) {
        if (initialized) return
        instance =
            FavoritesRecipesRepositoryImpl(
                RecipesMockApi(context.applicationContext),
            )
        initialized = true
    }
}
