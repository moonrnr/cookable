package com.example.cookable.ui.feature.recipeslist
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookable.domain.model.SortState
import com.example.cookable.ui.components.emptyfiltersstate.EmptyFiltersState
import com.example.cookable.ui.components.recipesloader.RecipesLoader
import com.example.cookable.ui.feature.bottomsheets.filterbottomsheet.FilterBottomSheet
import com.example.cookable.ui.feature.bottomsheets.filterbottomsheet.FilterBottomSheetState
import com.example.cookable.ui.feature.bottomsheets.sortbottomsheet.SortBottomSheet
import com.example.cookable.ui.feature.recipeslist.components.row.RecipesListItemRow
import com.example.cookable.ui.feature.recipeslist.components.topbar.RecipesListScreenTopBar
import com.example.cookable.ui.navigation.Routes
import com.example.cookable.ui.theme.Background

@Composable
fun RecipesListScreen(
    navController: NavController,
    viewModel: RecipesListViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    var showSortSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var draftFilters by remember { mutableStateOf(FilterBottomSheetState()) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Background),
    ) {
        RecipesListScreenTopBar(
            onBackClick = { navController.popBackStack(Routes.START, false) },
            onSortClick = { showSortSheet = true },
            onFilterClick = {
                draftFilters = state.filters
                showFilterSheet = true
            },
        )
        if (state.isLoading) {
            RecipesLoader(loaderText = "Searching for recipes")
        } else if (state.recipes.isEmpty()) {
            EmptyFiltersState()
        } else {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(top = 10.dp, bottom = 16.dp),
                ) {
                    items(state.recipes) { recipe ->
                        val isFavorite by viewModel
                            .isFavorite(recipe.id)
                            .collectAsState(false)

                        RecipesListItemRow(
                            recipe = recipe.copy(isFavorite = isFavorite),
                            recipesListType = RecipesListType.ALL_RECIPES,
                            onClick = {
                                navController.navigate(
                                    "${Routes.RECIPE_DETAILS}/${recipe.id}/${RecipesListType.ALL_RECIPES.name}",
                                )
                            },
                            onToggleFavorite = {
                                viewModel.toggleFavorite(recipe)
                            },
                        )
                    }
                }
            }
        }

        if (showSortSheet) {
            SortBottomSheet(
                state = SortState(selected = state.sortOption),
                onSelect = {
                    viewModel.setSortOption(it)
                    showSortSheet = false
                },
                onDismiss = { showSortSheet = false },
            )
        }

        if (showFilterSheet) {
            FilterBottomSheet(
                allTags = state.availableTags,
                state = draftFilters,
                onStateChange = { draftFilters = it },
                onApply = {
                    viewModel.setFilters(draftFilters)
                    showFilterSheet = false
                },
                onDismiss = {
                    showFilterSheet = false
                },
                onReset = {
                    val empty = FilterBottomSheetState.empty()
                    draftFilters = empty
                    viewModel.setFilters(empty)
                },
            )
        }
    }
}
