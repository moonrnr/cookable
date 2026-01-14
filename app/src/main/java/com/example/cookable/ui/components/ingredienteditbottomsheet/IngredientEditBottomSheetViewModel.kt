package com.example.cookable.ui.components.ingredienteditbottomsheet

import androidx.lifecycle.ViewModel
import com.example.cookable.domain.model.UnitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IngredientEditBottomSheetViewModel : ViewModel() {
    private val _state = MutableStateFlow(IngredientEditBottomSheetState())
    val state: StateFlow<IngredientEditBottomSheetState> = _state.asStateFlow()

    fun onNameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun onAmountChange(value: String) {
        _state.value = _state.value.copy(amount = value)
    }

    fun onUnitChange(unit: UnitType) {
        _state.value = _state.value.copy(unit = unit)
    }

    fun setSuggestions(
        amountSuggestion: String?,
        unitSuggestion: UnitType?,
    ) {
        _state.value =
            _state.value.copy(
                amountSuggestion = amountSuggestion,
                unitSuggestion = unitSuggestion,
            )
    }

    fun reset() {
        _state.value = IngredientEditBottomSheetState()
    }
}
