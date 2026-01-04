package com.example.cookable.ui.feature.start

import com.example.cookable.domain.model.Ingredient

data class StartState(
    val ingredients: List<Ingredient> = emptyList()
) {

    val canFindRecipes: Boolean
        get() = ingredients.isNotEmpty()

    val ingredientsCount: Int
        get() = ingredients.size

    val isEmpty: Boolean
        get() = ingredients.isEmpty()
}
