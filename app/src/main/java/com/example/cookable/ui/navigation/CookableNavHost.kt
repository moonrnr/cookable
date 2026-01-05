package com.example.cookable.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cookable.domain.repository.FavoritesRepositoryProvider
import com.example.cookable.ui.feature.favorites.FavoriteRecipesScreen
import com.example.cookable.ui.feature.imageprocessing.ImageProcessingScreen
import com.example.cookable.ui.feature.recipedetails.RecipeDetailsScreen
import com.example.cookable.ui.feature.recipeslist.RecipesListScreen
import com.example.cookable.ui.feature.recipeslist.RecipesListViewModel
import com.example.cookable.ui.feature.recognizedingredients.RecognizedIngredients
import com.example.cookable.ui.feature.start.StartScreen
import com.example.cookable.ui.feature.start.StartViewModel
import com.example.cookable.ui.scan.ScanScreen

@Composable
fun CookableNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val favoritesRepository =
        remember {
            FavoritesRepositoryProvider.instance
        }

    NavHost(
        navController,
        startDestination = Routes.START,
        modifier = modifier,
    ) {
        composable(Routes.START) { backStackEntry ->
            val startViewModel: StartViewModel = viewModel(backStackEntry)

            StartScreen(
                navController = navController,
                viewModel = startViewModel,
            )
        }

        composable(Routes.SCAN) {
            ScanScreen(
                onCancel = { navController.popBackStack() },
                onScan = { navController.navigate(Routes.PROCESSING) },
            )
        }

        composable(Routes.PROCESSING) {
            ImageProcessingScreen(
                onFinished = {
                    navController.navigate(Routes.RESULT) {
                        popUpTo(Routes.SCAN) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.RESULT) {
            val startBackStackEntry =
                remember(navController) {
                    navController.getBackStackEntry(Routes.START)
                }

            val startViewModel: StartViewModel = viewModel(startBackStackEntry)

            RecognizedIngredients(
                onConfirm = { recognizedIngredients ->

                    startViewModel.addIngredients(recognizedIngredients)

                    navController.popBackStack(Routes.START, false)
                },
                onBack = { navController.popBackStack(Routes.START, false) },
                onRescan = { navController.navigate(Routes.SCAN) },
            )
        }

        composable(Routes.RECIPES_LIST) {
            RecipesListScreen(
                navController = navController,
                favoritesRepository = favoritesRepository,
            )
        }

        composable(
            route = "${Routes.RECIPE_DETAILS}/{recipeId}",
            arguments =
                listOf(
                    navArgument("recipeId") { type = NavType.StringType },
                ),
        ) { backStackEntry ->

            val recipeId =
                backStackEntry.arguments?.getString("recipeId")
                    ?: return@composable

            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.RECIPES_LIST)
                }

            val recipesListViewModel: RecipesListViewModel =
                viewModel(parentEntry)

            val recipe =
                recipesListViewModel.state.value.recipes
                    .firstOrNull { it.id == recipeId }
                    ?: return@composable

            RecipeDetailsScreen(
                recipe = recipe,
                navController = navController,
                favoritesRepository = favoritesRepository,
            )
        }

        composable(Routes.FAVORITE_RECIPES_LIST) {
            FavoriteRecipesScreen(
                navController = navController,
                favoritesRepository = favoritesRepository,
            )
        }
    }
}
