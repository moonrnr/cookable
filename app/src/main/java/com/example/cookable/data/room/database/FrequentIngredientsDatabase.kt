package com.example.cookable.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cookable.data.room.dao.FrequentIngredientsDao
import com.example.cookable.data.room.entity.AmountSuggestionEntity
import com.example.cookable.data.room.entity.IngredientEntity
import com.example.cookable.data.room.entity.UnitSuggestionEntity

@Database(
    entities = [
        IngredientEntity::class,
        AmountSuggestionEntity::class,
        UnitSuggestionEntity::class,
    ],
    version = 1,
)
abstract class FrequentIngredientsDatabase : RoomDatabase() {
    abstract fun dao(): FrequentIngredientsDao
}
