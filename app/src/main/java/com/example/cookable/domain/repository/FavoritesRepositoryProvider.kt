package com.example.cookable.domain.repository

object FavoritesRepositoryProvider {
    val instance: FavoritesRecipesRepository =
        FavoritesRecipesRepositoryImpl()
}
