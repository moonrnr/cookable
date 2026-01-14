package com.example.cookable.ui.feature.start.components.bottomsheets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.model.IngredientBottomSheetType
import com.example.cookable.domain.repository.FrequentIngredientsRepositoryProvider
import com.example.cookable.ui.components.ingredientaddbottomsheet.IngredientAddBottomSheet
import com.example.cookable.ui.components.ingredientaddbottomsheet.IngredientAddBottomSheetViewModel
import com.example.cookable.ui.components.ingredienteditbottomsheet.IngredientEditBottomSheet

@Composable
fun IngredientBottomSheetDispatcher(
    ingredient: Ingredient?,
    editedIndex: Int?,
    onAdd: (Ingredient) -> Unit,
    onEdit: (Int, Ingredient) -> Unit,
    onDismiss: () -> Unit,
) {
    ingredient ?: return

    if (editedIndex == null) {
        val ingredientAddBottomSheetViewModel =
            remember {
                IngredientAddBottomSheetViewModel(
                    repository = FrequentIngredientsRepositoryProvider.get(),
                )
            }

        IngredientAddBottomSheet(
            viewModel = ingredientAddBottomSheetViewModel,
            onSave = { result ->
                onAdd(result)
                onDismiss()
            },
            onCancel = onDismiss,
            onDismiss = onDismiss,
        )
    } else {
        IngredientEditBottomSheet(
            initialIngredient = ingredient,
            ingredientBottomSheetType = IngredientBottomSheetType.EDIT,
            onSave = { result ->
                onEdit(editedIndex, result)
                onDismiss()
            },
            onCancel = onDismiss,
            onDismiss = onDismiss,
        )
    }
}
