package com.example.cookable.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cookable.domain.repository.FavoritesRepositoryProvider
import com.example.cookable.domain.repository.RecipesRepositoryProvider
import com.example.cookable.ui.feature.favorites.FavoriteRecipesScreen
import com.example.cookable.ui.feature.imageprocessing.ImageProcessingScreen
import com.example.cookable.ui.feature.recipedetails.RecipeDetailsScreen
import com.example.cookable.ui.feature.recipeslist.RecipesListScreen
import com.example.cookable.ui.feature.recognizedingredients.RecognizedIngredients
import com.example.cookable.ui.feature.scan.ScanScreen
import com.example.cookable.ui.feature.start.StartScreen
import com.example.cookable.ui.feature.start.StartViewModel
import com.example.cookable.ui.scan.ScanViewModel

@Composable
fun CookableNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current

    SideEffect {
        RecipesRepositoryProvider.init(context.applicationContext)
    }

    SideEffect {
        FavoritesRepositoryProvider.init(context.applicationContext)
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
            val scanViewModel: ScanViewModel = viewModel()
            ScanScreen(
                onCancel = { navController.popBackStack() },
                onScan = { navController.navigate(Routes.PROCESSING) },
                scanViewModel = scanViewModel,
            )
        }

        composable(Routes.PROCESSING) { backStackEntry ->

            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Routes.SCAN)
                }

            val scanViewModel: ScanViewModel =
                viewModel(parentEntry)

            ImageProcessingScreen(
                scanViewModel = scanViewModel,
                onFinished = {
                    navController.navigate(Routes.RESULT)
                },
            )
        }

        composable(Routes.RESULT) {
            val startBackStackEntry =
                remember(navController) {
                    navController.getBackStackEntry(Routes.START)
                }
            val startViewModel: StartViewModel =
                viewModel(startBackStackEntry)

            val scanBackStackEntry =
                remember(navController) {
                    navController.getBackStackEntry(Routes.SCAN)
                }
            val scanViewModel: ScanViewModel =
                viewModel(scanBackStackEntry)

            RecognizedIngredients(
                onConfirm = { recognizedIngredients ->
                    startViewModel.addIngredients(recognizedIngredients)

                    navController.popBackStack(Routes.START, false)
                },
                onBack = {
                    navController.popBackStack(Routes.START, false)
                },
                onRescan = {
                    scanViewModel.clearPhoto()

                    navController.popBackStack(Routes.SCAN, false)
                },
                scanViewModel = scanViewModel,
            )
        }

        composable(Routes.RECIPES_LIST) {
            RecipesListScreen(
                navController = navController,
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

            val recipesRepository =
                RecipesRepositoryProvider.instance

            val recipe =
                recipesRepository.recipes.value
                    .firstOrNull { it.id == recipeId }
                    ?: return@composable

            RecipeDetailsScreen(
                recipe = recipe,
                navController = navController,
            )
        }

        composable(Routes.FAVORITE_RECIPES_LIST) {
            FavoriteRecipesScreen(
                navController = navController,
            )
        }
    }
}
