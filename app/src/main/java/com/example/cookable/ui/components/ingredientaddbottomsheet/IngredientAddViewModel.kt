package com.example.cookable.ui.components.ingredientaddbottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.model.UnitType
import com.example.cookable.domain.repository.FrequentIngredientsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IngredientAddViewModel(
    private val repository: FrequentIngredientsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(IngredientAddState())
    val state: StateFlow<IngredientAddState> = _state.asStateFlow()

    fun onNameChanged(value: String) {
        _state.update {
            it.copy(
                name = value,
                isSaveEnabled = value.isNotBlank(),
            )
        }

        viewModelScope.launch {
            val suggestions = repository.searchByName(value)
            _state.update { it.copy(nameSuggestions = suggestions) }
        }
    }

    fun onNameSuggestionClicked(name: String) {
        _state.update {
            it.copy(
                name = name,
                nameSuggestions = emptyList(),
            )
        }

        viewModelScope.launch {
            val ingredient = repository.getFrequentIngredient(name)

            _state.update {
                it.copy(
                    suggestedAmounts = ingredient?.suggestedAmounts.orEmpty(),
                    suggestedUnits = ingredient?.suggestedUnits.orEmpty(),
                )
            }
        }
    }

    fun onAmountChanged(value: String) {
        _state.update { it.copy(amount = value) }
    }

    fun onUnitChanged(unit: UnitType) {
        _state.update { it.copy(unit = unit) }
    }

    fun onSuggestedAmountClicked(amount: Double) {
        _state.update { it.copy(amount = amount.toString()) }
    }

    fun onSuggestedUnitClicked(unit: UnitType) {
        _state.update { it.copy(unit = unit) }
    }

    fun onSaveClicked(onSaved: (Ingredient) -> Unit) {
        val state = _state.value
        val ingredient =
            Ingredient(
                name = state.name,
                amount = state.amount.toDoubleOrNull(),
                unit = state.unit,
            )

        viewModelScope.launch {
            repository.saveIngredientVariant(
                name = ingredient.name,
                amount = ingredient.amount,
                unit = ingredient.unit,
            )
            onSaved(ingredient)
        }
    }
}
