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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cookable.domain.repository.FavoritesRecipesRepository
import com.example.cookable.ui.components.ArrowBackIconButton
import com.example.cookable.ui.components.EmptyFavoritesState
import com.example.cookable.ui.components.ScreenTitle
import com.example.cookable.ui.feature.recipeslist.RecipesListItemRow
import com.example.cookable.ui.feature.recipeslist.RecipesListType
import com.example.cookable.ui.navigation.Routes
import com.example.cookable.ui.theme.Background

@Composable
fun FavoriteRecipesScreen(
    navController: NavController,
    favoritesRepository: FavoritesRecipesRepository,
) {
    val favorites by favoritesRepository.favorites.collectAsState()

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
            ArrowBackIconButton({ navController.popBackStack() })
            Spacer(modifier = Modifier.width(8.dp))
            ScreenTitle(text = "Favorites")
        }

        if (favorites.isEmpty()) {
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
                    key = { it.id },
                ) { recipe ->

                    RecipesListItemRow(
                        recipe = recipe.copy(isFavorite = true),
                        recipesListType = RecipesListType.FAVORITES,
                        onClick = {
                            navController.navigate(
                                "${Routes.RECIPE_DETAILS}/${recipe.id}",
                            )
                        },
                        onToggleFavorite = {
                            favoritesRepository.toggleFavorite(recipe)
                        },
                    )
                }
            }
        }
    }
}
