package com.example.cookable.ui.feature.start.components.ingredients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.core.extensions.formatAmount
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.ui.components.arrowsdownwardsicons.ArrowsDownwardsIcons
import com.example.cookable.ui.components.ingredientrow.IngredientRow
import com.example.cookable.ui.components.ingredientscountbadge.IngredientsCountBadge
import com.example.cookable.ui.theme.Muted
import com.example.cookable.ui.theme.PrimaryGreen
import com.example.cookable.ui.theme.PrimaryGreenLight

@Composable
fun IngredientsList(
    ingredients: List<Ingredient>,
    listState: LazyListState,
    showScrollHint: Boolean,
    onIngredientClick: (Ingredient, Int) -> Unit,
    onRemoveIngredient: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
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
                ingredientsCount = ingredients.size,
                backgroundColor = PrimaryGreenLight,
                textColor = PrimaryGreen,
            )
        }

        if (ingredients.isEmpty()) {
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
                    state = listState,
                    modifier =
                        Modifier
                            .heightIn(max = maxHeight * 0.7f),
                ) {
                    itemsIndexed(ingredients) { index, ingredient ->
                        IngredientRow(
                            name = ingredient.name,
                            amount = ingredient.amount?.formatAmount() ?: "",
                            unit = ingredient.unit,
                            onClick = {
                                onIngredientClick(ingredient, index)
                            },
                            onRemove = { onRemoveIngredient(index) },
                            onAcceptSuggestion = {},
                        )
                    }
                }
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter),
                ) {
                    if (showScrollHint) {
                        ArrowsDownwardsIcons()
                    }
                }
            }
        }
    }
}
