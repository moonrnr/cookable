package com.example.cookable.ui.feature.recognizedingredients

import androidx.lifecycle.ViewModel
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.model.UnitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecognizedIngredientsViewModel : ViewModel() {
    private val _ingredients =
        MutableStateFlow(
            listOf(
                Ingredient(
                    name = "Tomato",
                    amount = null,
                    unit = null,
                    amountSuggestion = 2.0,
                    unitSuggestion = UnitType.PIECE,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Apple",
                    amount = null,
                    unit = null,
                    amountSuggestion = 3.0,
                    unitSuggestion = UnitType.PIECE,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Egg",
                    amount = null,
                    unit = null,
                    amountSuggestion = 4.0,
                    unitSuggestion = UnitType.PIECE,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Onion",
                    amount = null,
                    unit = null,
                    amountSuggestion = 1.0,
                    unitSuggestion = UnitType.PIECE,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Cheese",
                    amount = 150.0,
                    unit = UnitType.GRAM,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Water",
                    amount = 1.5,
                    unit = UnitType.LITER,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Flour",
                    amount = 500.0,
                    unit = UnitType.GRAM,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Milk",
                    amount = 1.0,
                    unit = UnitType.LITER,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Butter",
                    amount = 50.0,
                    unit = UnitType.GRAM,
                    isRecognized = true,
                ),
                Ingredient(
                    name = "Olive Oil",
                    amount = 30.0,
                    unit = UnitType.MILLILITER,
                    isRecognized = true,
                ),
            ),
        )

    val ingredients: StateFlow<List<Ingredient>> = _ingredients.asStateFlow()
    private val _showErrors = MutableStateFlow(false)
    val showErrors = _showErrors.asStateFlow()

    fun remove(index: Int) {
        _ingredients.value =
            _ingredients.value.toMutableList().also {
                it.removeAt(index)
            }
    }

    fun isValid(): Boolean = _ingredients.value.none { it.amount == null || it.unit == null }

    fun validateAndMarkErrors(): Boolean {
        _showErrors.value = true

        val updated =
            _ingredients.value.map { ingredient ->
                val hasError = ingredient.amount == null || ingredient.unit == null
                ingredient.copy(hasError = hasError)
            }

        _ingredients.value = updated

        return updated.none { it.hasError }
    }

    fun update(
        index: Int,
        updated: Ingredient,
    ) {
        _ingredients.value =
            _ingredients.value.mapIndexed { i, ingredient ->
                if (i == index) updated else ingredient
            }
    }
}
