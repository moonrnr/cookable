package com.example.cookable.domain.repository

import android.content.Context
import androidx.room.Room
import com.example.cookable.data.room.database.FrequentIngredientsDatabase

object FrequentIngredientsRepositoryProvider {
    private lateinit var repository: FrequentIngredientsRepository

    fun init(context: Context) {
        if (::repository.isInitialized) return

        val database =
            Room
                .databaseBuilder(
                    context,
                    FrequentIngredientsDatabase::class.java,
                    "frequent_ingredients_db",
                ).build()

        repository = FrequentIngredientsRepositoryImpl(database.dao())
    }

    fun get(): FrequentIngredientsRepository {
        check(::repository.isInitialized) {
            "FrequentIngredientsRepositoryProvider is not initialized"
        }
        return repository
    }
}
