package com.example.cookable.ui.feature.start

import androidx.lifecycle.ViewModel
import com.example.cookable.domain.model.Ingredient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StartScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(StartScreenState())
    val state: StateFlow<StartScreenState> = _state.asStateFlow()

    fun onRemoveIngredient(index: Int) {
        _state.value =
            _state.value.copy(
                ingredients =
                    _state.value.ingredients.toMutableList().also {
                        it.removeAt(index)
                    },
            )
    }

    fun addIngredient(ingredient: Ingredient) {
        _state.value =
            _state.value.copy(
                ingredients = _state.value.ingredients + ingredient,
            )
    }

    fun updateIngredient(
        index: Int,
        updated: Ingredient,
    ) {
        _state.value =
            _state.value.copy(
                ingredients =
                    _state.value.ingredients.mapIndexed { i, ingredient ->
                        if (i == index) updated else ingredient
                    },
            )
    }

    fun addIngredients(newIngredients: List<Ingredient>) {
        _state.value =
            _state.value.copy(
                ingredients = _state.value.ingredients + newIngredients,
            )
    }
}
