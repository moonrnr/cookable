package com.example.cookable.ui.feature.recognizedingredients.components.list

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.cookable.core.extensions.formatAmount
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.ui.components.ingredients.ingredientrow.IngredientRow
import com.example.cookable.ui.components.scrollcaption.ArrowsDownwardsIcons
import com.example.cookable.ui.recognizedingredientssummary.RecognizedIngredientsSummary
import com.example.cookable.ui.theme.Card
import com.example.cookable.ui.theme.Line

@Composable
fun RecognizedIngredientsList(
    ingredients: List<Ingredient>,
    photoUri: Uri?,
    listState: LazyListState,
    showScrollHint: Boolean,
    completeCount: Int,
    incompleteCount: Int,
    showErrors: Boolean,
    onIngredientClick: (Int) -> Unit,
    onRemoveIngredient: (Int) -> Unit,
    onAcceptSuggestion: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(10.dp, RoundedCornerShape(18.dp))
                .background(Card, RoundedCornerShape(18.dp)),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Line),
            contentAlignment = Alignment.Center,
        ) {
            photoUri?.let { uri ->
                coil.compose.AsyncImage(
                    model = uri,
                    contentDescription = "Scanned image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                )
            }
        }

        RecognizedIngredientsSummary(
            completeCount = completeCount,
            incompleteCount = incompleteCount,
        )
        HorizontalDivider(color = Line)

        BoxWithConstraints {
            LazyColumn(
                state = listState,
                modifier =
                    Modifier
                        .heightIn(max = maxHeight * 0.78f),
            ) {
                itemsIndexed(ingredients) { index, ingredient ->
                    IngredientRow(
                        name = ingredient.name,
                        amount = ingredient.amount?.formatAmount() ?: "",
                        unit = ingredient.unit,
                        onClick = { onIngredientClick(index) },
                        onRemove = { onRemoveIngredient(index) },
                        suggestedUnit = ingredient.unitSuggestion,
                        suggestedAmount = ingredient.amountSuggestion?.toString(),
                        hasError = showErrors && ingredient.hasError,
                        onAcceptSuggestion = { onAcceptSuggestion(index) },
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
