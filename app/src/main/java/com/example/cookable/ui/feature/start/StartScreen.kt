package com.example.cookable.ui.feature.start

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
import androidx.navigation.NavController
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.repository.FavoritesRepositoryProvider
import com.example.cookable.ui.feature.start.components.actions.AddManuallyButton
import com.example.cookable.ui.feature.start.components.actions.FindRecipesButton
import com.example.cookable.ui.feature.start.components.actions.ScanIngredientsButton
import com.example.cookable.ui.feature.start.components.apphelp.AppHelp
import com.example.cookable.ui.feature.start.components.bottomsheets.IngredientBottomSheetDispatcher
import com.example.cookable.ui.feature.start.components.ingredients.IngredientsList
import com.example.cookable.ui.feature.start.components.topbar.StartScreenTopBar
import com.example.cookable.ui.navigation.Routes
import com.example.cookable.ui.theme.Background

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: StartScreenViewModel,
) {
    val state by viewModel.state.collectAsState()

    var sheetIngredient by remember { mutableStateOf<Ingredient?>(null) }
    var editedIndex by remember { mutableStateOf<Int?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

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
                .background(Background)
                .padding(top = 20.dp),
    ) {
        StartScreenTopBar(
            onHelpClick = { showInfoDialog = true },
            onFavoritesClick = {
                val hasIngredients = state.ingredients.isNotEmpty()
                FavoritesRepositoryProvider.instance
                    .setFilterByIngredients(hasIngredients)
                navController.navigate(Routes.FAVORITE_RECIPES_LIST)
            },
        )

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            IngredientsList(
                ingredients = state.ingredients,
                listState = listState,
                showScrollHint = showScrollHint,
                onIngredientClick = { ingredient, index ->
                    sheetIngredient = ingredient
                    editedIndex = index
                },
                onRemoveIngredient = { index ->
                    viewModel.onRemoveIngredient(index)
                },
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ScanIngredientsButton(onClick = { navController.navigate(Routes.SCAN) })
                AddManuallyButton(
                    onClick = {
                        sheetIngredient = Ingredient.empty()
                        editedIndex = null
                    },
                )
            }
        }

        if (state.canFindRecipes) {
            FindRecipesButton(onClick = { navController.navigate(Routes.RECIPES_LIST) })
        }
    }
    IngredientBottomSheetDispatcher(
        ingredient = sheetIngredient,
        editedIndex = editedIndex,
        onAdd = { result ->
            viewModel.addIngredient(result)
            sheetIngredient = null
            editedIndex = null
        },
        onEdit = { index, result ->
            viewModel.updateIngredient(index, result)
            sheetIngredient = null
            editedIndex = null
        },
        onDismiss = {
            sheetIngredient = null
            editedIndex = null
        },
    )
    AppHelp(
        isVisible = showInfoDialog,
        onDismiss = { showInfoDialog = false },
    )
}
