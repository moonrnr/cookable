package com.example.cookable.ui.feature.start

import android.R.attr.maxHeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookable.core.extensions.formatAmount
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.model.IngredientBottomSheetType
import com.example.cookable.domain.repository.FavoritesRepositoryProvider
import com.example.cookable.ui.components.apphelp.AppHelp
import com.example.cookable.ui.components.applogo.AppLogo
import com.example.cookable.ui.components.ingredientbottomsheet.IngredientBottomSheet
import com.example.cookable.ui.components.ingredientrow.IngredientRow
import com.example.cookable.ui.components.ingredientscountbadge.IngredientsCountBadge
import com.example.cookable.ui.navigation.Routes
import com.example.cookable.ui.theme.Background
import com.example.cookable.ui.theme.Muted
import com.example.cookable.ui.theme.PrimaryGreen
import com.example.cookable.ui.theme.PrimaryGreenLight
import com.example.cookable.ui.theme.PrimaryOrange

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: StartViewModel,
) {
    val state by viewModel.state.collectAsState()

    var sheetIngredient by remember { mutableStateOf<Ingredient?>(null) }
    var editedIndex by remember { mutableStateOf<Int?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Background)
                .padding(top = 20.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 0.dp, bottom = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            AppLogo()

            Row {
                IconButton(onClick = { showInfoDialog = true }) {
                    Icon(Icons.Filled.Help, contentDescription = null, tint = Color(0xFF2E7D32))
                }
                IconButton(
                    onClick = {
                        val hasIngredients = state.ingredients.isNotEmpty()
                        FavoritesRepositoryProvider.instance
                            .setFilterByIngredients(hasIngredients)
                        navController.navigate(Routes.FAVORITE_RECIPES_LIST)
                    },
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = null, tint = Color(0xFF2E7D32))
                }
            }
        }

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .shadow(10.dp, RoundedCornerShape(18.dp))
                        .background(Color.White, RoundedCornerShape(18.dp)),
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Your ingredients",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    IngredientsCountBadge(
                        ingredientsCount = state.ingredients.size,
                        backgroundColor = PrimaryGreenLight,
                        textColor = PrimaryGreen,
                    )
                }

                if (state.ingredients.isEmpty()) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Nothing here yet",
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Start by adding some ingredients",
                            fontSize = 14.sp,
                            color = Muted,
                        )
                    }
                } else {
                    BoxWithConstraints {
                        LazyColumn(
                            modifier =
                                Modifier
                                    .heightIn(max = maxHeight * 0.7f),
                        ) {
                            itemsIndexed(state.ingredients) { index, ingredient ->
                                IngredientRow(
                                    name = ingredient.name,
                                    amount = ingredient.amount?.formatAmount() ?: "",
                                    unit = ingredient.unit,
                                    onClick = {
                                        sheetIngredient = ingredient
                                        editedIndex = index
                                    },
                                    onRemove = { viewModel.onRemoveIngredient(index) },
                                    onAcceptSuggestion = {},
                                )
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { navController.navigate(Routes.SCAN) },
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = PrimaryGreen,
                            contentColor = Color.White,
                        ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                ) {
                    Text("Scan ingredients", fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {
                        sheetIngredient = Ingredient.empty()
                        editedIndex = null
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = PrimaryGreenLight,
                            contentColor = PrimaryGreen,
                        ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                ) {
                    Text("Add manually", fontWeight = FontWeight.Bold)
                }
            }
        }
//        if (state.canFindRecipes) {
        if (true) {
            Button(
                onClick = { navController.navigate(Routes.RECIPES_LIST) },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = PrimaryOrange,
                        contentColor = Color.White,
                    ),
                shape = RoundedCornerShape(18.dp),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp)
                        .height(54.dp),
            ) {
                Text(
                    text = "Find recipes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }

        sheetIngredient?.let { ingredient ->

            IngredientBottomSheet(
                initialIngredient = ingredient,
                onDismiss = {
                    sheetIngredient = null
                    editedIndex = null
                },
                onCancel = {
                    sheetIngredient = null
                    editedIndex = null
                },
                onSave = { result ->
                    if (editedIndex == null) {
                        viewModel.addIngredient(result)
                    } else {
                        viewModel.updateIngredient(editedIndex!!, result)
                    }

                    sheetIngredient = null
                    editedIndex = null
                },
                ingredientBottomSheetType = IngredientBottomSheetType.ADD,
            )
        }
    }
    AppHelp(
        isVisible = showInfoDialog,
        onDismiss = { showInfoDialog = false },
    )
}
