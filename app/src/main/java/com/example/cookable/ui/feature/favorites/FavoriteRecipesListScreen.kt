package com.example.cookable.ui.feature.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookable.domain.repository.FavoritesRecipesRepository
import com.example.cookable.ui.components.EmptyFavoritesState
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
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = 10.dp,
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }

            Text(
                text = "Favorites",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
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
