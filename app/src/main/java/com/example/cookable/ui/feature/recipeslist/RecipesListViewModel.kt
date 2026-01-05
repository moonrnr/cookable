package com.example.cookable.ui.feature.recipeslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookable.data.remote.api.RecipesMockApi
import com.example.cookable.domain.repository.RecipesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipesListViewModel(
    app: Application,
) : AndroidViewModel(app) {
    private val repository =
        RecipesRepositoryImpl(
            RecipesMockApi(app),
        )

    private val _state = MutableStateFlow(RecipesListState())
    val state = _state.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _state.value = RecipesListState(isLoading = true)
            val recipes = repository.getRecipes()
            _state.value =
                RecipesListState(
                    isLoading = false,
                    recipes = recipes,
                )
        }
    }
}
