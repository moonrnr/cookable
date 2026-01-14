package com.example.cookable.ui.feature.recognizedingredients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.model.IngredientBottomSheetType
import com.example.cookable.ui.components.ingredienteditbottomsheet.IngredientEditBottomSheet
import com.example.cookable.ui.feature.recognizedingredients.components.actions.ConfirmButton
import com.example.cookable.ui.feature.recognizedingredients.components.actions.RescanButton
import com.example.cookable.ui.feature.recognizedingredients.components.modals.MissingDataModal
import com.example.cookable.ui.feature.recognizedingredients.components.recognizedingredientslist.RecognizedIngredientsList
import com.example.cookable.ui.feature.recognizedingredients.components.topbar.RecognizedIngredientsTopbar
import com.example.cookable.ui.scan.ScanViewModel
import com.example.cookable.ui.theme.Background

@Composable
fun RecognizedIngredients(
    onConfirm: (List<Ingredient>) -> Unit,
    onBack: () -> Unit,
    onRescan: () -> Unit,
    scanViewModel: ScanViewModel,
    viewModel: RecognizedIngredientsViewModel = viewModel(),
) {
    val ingredients by viewModel.ingredients.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    var editedIngredientIndex by remember { mutableStateOf<Int?>(null) }
    val photoUri by scanViewModel.photoUri.collectAsState()
    val showErrors by viewModel.showErrors.collectAsState()
    val completeCount =
        ingredients.count {
            it.unitSuggestion == null && it.amountSuggestion == null
        }

    val incompleteCount = ingredients.size - completeCount

    val listState = rememberLazyListState()

    val isScrollable by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val visibleItems = layoutInfo.visibleItemsInfo

            if (visibleItems.isEmpty()) return@derivedStateOf false

            val lastVisibleIndex = visibleItems.last().index
            lastVisibleIndex < totalItems - 1
        }
    }

    val hasScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 ||
                listState.firstVisibleItemScrollOffset > 0
        }
    }
    val showScrollHint = isScrollable && !hasScrolled

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Background),
    ) {
        RecognizedIngredientsTopbar(
            ingredientsCount = ingredients.size,
            onBack = onBack,
        )

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            RecognizedIngredientsList(
                ingredients = ingredients,
                photoUri = photoUri,
                listState = listState,
                showScrollHint = showScrollHint,
                completeCount = completeCount,
                incompleteCount = incompleteCount,
                showErrors = showErrors,
                onIngredientClick = { editedIngredientIndex = it },
                onRemoveIngredient = viewModel::remove,
                onAcceptSuggestion = viewModel::acceptSuggestion,
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ConfirmButton(
                    onClick = {
                        if (viewModel.validateAndMarkErrors()) {
                            onConfirm(ingredients)
                        } else {
                            showErrorDialog = true
                        }
                    },
                )
                RescanButton(
                    onClick = onRescan,
                )
            }
        }
    }

    if (showErrorDialog) {
        MissingDataModal(
            onDismiss = { showErrorDialog = false },
        )
    }

    editedIngredientIndex?.let { index ->
        IngredientEditBottomSheet(
            initialIngredient = ingredients[index],
            onSave = { updatedIngredient ->
                viewModel.update(index, updatedIngredient)
                editedIngredientIndex = null
            },
            onCancel = {
                editedIngredientIndex = null
            },
            onDismiss = {
                editedIngredientIndex = null
            },
            ingredientBottomSheetType = IngredientBottomSheetType.EDIT,
        )
    }
}
