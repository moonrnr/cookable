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
                    hasError = true,
                ),
                Ingredient(
                    name = "Cheese",
                    amount = 100.0,
                    unit = UnitType.GRAM,
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
