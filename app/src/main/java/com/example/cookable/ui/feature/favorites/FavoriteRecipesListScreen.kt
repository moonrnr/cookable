package com.example.cookable.ui.feature.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookable.domain.model.SortState
import com.example.cookable.ui.components.emptyfavoritesstate.EmptyFavoritesState
import com.example.cookable.ui.components.filterbottomsheet.FilterBottomSheet
import com.example.cookable.ui.components.filterbottomsheet.FilterBottomSheetState
import com.example.cookable.ui.components.iconbuttons.arrows.ArrowBackIconButton
import com.example.cookable.ui.components.recipesloader.RecipesLoader
import com.example.cookable.ui.components.screentitle.ScreenTitle
import com.example.cookable.ui.components.sortbottomsheet.SortBottomSheet
import com.example.cookable.ui.components.sortfiltercontainer.SortFilterContainer
import com.example.cookable.ui.feature.recipeslist.RecipesListItemRow
import com.example.cookable.ui.feature.recipeslist.RecipesListType
import com.example.cookable.ui.navigation.Routes
import com.example.cookable.ui.theme.Background

@Composable
fun FavoriteRecipesScreen(
    navController: NavController,
    viewModel: FavoriteRecipesListViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val favorites = state.recipes
    var showSortSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var draftFilters by remember { mutableStateOf(FilterBottomSheetState()) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Background),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 12.dp,
                        end = 20.dp,
                        top = 12.dp,
                        bottom = 10.dp,
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ArrowBackIconButton({ navController.popBackStack() })
            Spacer(modifier = Modifier.width(8.dp))
            ScreenTitle(text = "Favorites")
            Spacer(modifier = Modifier.width(30.dp))
            SortFilterContainer(
                onSortClick = { showSortSheet = true },
                onFilterClick = {
                    draftFilters = state.filters
                    showFilterSheet = true
                },
            )
        }

        if (state.isLoading) {
            RecipesLoader(loaderText = "Loading favorite recipes")
        } else if (favorites.isEmpty()) {
            EmptyFavoritesState()
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 10.dp, bottom = 16.dp),
            ) {
                items(
                    items = favorites,
                ) { recipe ->

                    RecipesListItemRow(
                        recipe = recipe.copy(isFavorite = true),
                        recipesListType = RecipesListType.FAVORITES,
                        onClick = {
                            navController.navigate(
                                "${Routes.RECIPE_DETAILS}/${recipe.id}/${RecipesListType.FAVORITES.name}",
                            )
                        },
                        onToggleFavorite = {
                            viewModel.toggleFavorite(recipe)
                        },
                    )
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
