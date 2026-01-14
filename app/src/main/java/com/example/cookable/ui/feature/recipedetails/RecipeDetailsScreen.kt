package com.example.cookable.ui.feature.recipedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookable.domain.model.Recipe
import com.example.cookable.ui.components.chip.Chip
import com.example.cookable.ui.components.iconbuttons.arrows.ArrowBackIconButton
import com.example.cookable.ui.components.iconbuttons.favorite.AddToFavoriteIconButton
import com.example.cookable.ui.components.ingredientslist.IngredientsList
import com.example.cookable.ui.components.matchbadge.MatchBadge
import com.example.cookable.ui.components.missingingredientsbox.MissingIngredientsBox
import com.example.cookable.ui.components.totaltimebadge.TotalTimeBadge
import com.example.cookable.ui.feature.recipedetails.components.sectiontitle.SectionTitle
import com.example.cookable.ui.feature.recipeslist.RecipesListType
import com.example.cookable.ui.theme.Background
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.White

@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    navController: NavController,
    listType: RecipesListType,
    viewModel: RecipeDetailsViewModel = viewModel(),
) {
    val isFavorite by viewModel
        .isFavorite(recipe.id)
        .collectAsState(false)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Background),
    ) {
        Box {
            AsyncImage(
                model = recipe.imgSrc,
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ArrowBackIconButton(
                    onClick = { navController.popBackStack() },
                    modifier =
                        Modifier
                            .background(
                                color = White.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(50),
                            ),
                )

                AddToFavoriteIconButton(isFavorite = isFavorite, isSmall = false, onClick = { viewModel.toggleFavorite(recipe) })
            }

            Row(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MatchBadge(
                    matchPercentage = recipe.matchPercentage,
                    level = recipe.matchLevel,
                )
                TotalTimeBadge(totalTime = recipe.totalTime)
            }
        }

        Column {
            Column(
                modifier =
                    Modifier
                        .background(Background)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = recipe.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    recipe.tags.forEach { tag ->
                        Chip(text = tag)
                    }
                }

                HorizontalDivider(color = Line)
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                if (listType == RecipesListType.ALL_RECIPES) {
                    item {
                        MissingIngredientsBox(
                            hasAllIngredients = recipe.hasAllIngredients,
                            missingIngredients = recipe.missingIngredients,
                        )
                    }

                    item {
                        HorizontalDivider(color = Line)
                    }
                }

                stickyHeader {
                    SectionTitle("Ingredients", modifier = Modifier.background(Background).fillMaxWidth().padding(bottom = 10.dp))
                }

                item {
                    println("TO RECIPES, recipes")
                    println(recipe)
                    IngredientsList(recipe.ingredients, recipe.missingIngredients)
                }

                item {
                    HorizontalDivider(color = Line)
                }

                stickyHeader {
                    SectionTitle("Directions", modifier = Modifier.background(Background).fillMaxWidth().padding(bottom = 10.dp))
                }
                item {
                    Text(
                        text = recipe.directions,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
