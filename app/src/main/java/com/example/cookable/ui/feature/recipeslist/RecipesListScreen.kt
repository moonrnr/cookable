package com.example.cookable.ui.feature.recipeslist
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.cookable.domain.repository.FavoritesRecipesRepository
import com.example.cookable.ui.components.emptyfiltersstate.EmptyFiltersState
import com.example.cookable.ui.components.filterbottomsheet.FilterBottomSheet
import com.example.cookable.ui.components.filterbottomsheet.FilterBottomSheetState
import com.example.cookable.ui.components.iconbutton.arrowbackiconbutton.ArrowBackIconButton
import com.example.cookable.ui.components.screentitle.ScreenTitle
import com.example.cookable.ui.components.sortbottomsheet.SortBottomSheet
import com.example.cookable.ui.components.sortfiltercontainer.SortFilterContainer
import com.example.cookable.ui.navigation.Routes
import com.example.cookable.ui.theme.Background
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun RecipesListScreen(
    navController: NavController,
    favoritesRepository: FavoritesRecipesRepository,
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
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        end = 20.dp,
                        top = 12.dp,
                        bottom = 10.dp,
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ArrowBackIconButton({ navController.popBackStack(Routes.START, false) })
            Spacer(modifier = Modifier.width(8.dp))
            ScreenTitle(text = "Recipes")
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
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(color = PrimaryGreen)
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Searching for recipes",
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
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
                        val isFavorite by favoritesRepository
                            .isFavorite(recipe.id)
                            .collectAsState(false)

                        RecipesListItemRow(
                            recipe = recipe.copy(isFavorite = isFavorite),
                            recipesListType = RecipesListType.ALL_RECIPES,
                            onClick = {
                                navController.navigate("${Routes.RECIPE_DETAILS}/${recipe.id}")
                            },
                            onToggleFavorite = {
                                favoritesRepository.toggleFavorite(recipe)
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
                    draftFilters = FilterBottomSheetState.empty()
                },
            )
        }
    }
}
