package com.example.cookable.domain.repository

import android.content.Context
import com.example.cookable.data.remote.api.RecipesMockApi

object RecipesRepositoryProvider {
    private var initialized = false
    lateinit var instance: RecipesRepository

    fun init(context: Context) {
        if (initialized) return
        instance = RecipesRepositoryImpl(RecipesMockApi(context.applicationContext))
        initialized = true
    }
}
