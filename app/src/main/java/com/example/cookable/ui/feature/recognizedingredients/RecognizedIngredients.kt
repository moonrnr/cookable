package com.example.cookable.ui.feature.recognizedingredients

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookable.core.extensions.formatAmount
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.ui.components.IngredientBottomSheet
import com.example.cookable.ui.components.IngredientRow
import com.example.cookable.ui.theme.*

@Composable
fun RecognizedIngredients(
    onConfirm: (List<Ingredient>) -> Unit,
    onBack: () -> Unit,
    onRescan: () -> Unit,
    viewModel: RecognizedIngredientsViewModel = viewModel()
) {
    val ingredients by viewModel.ingredients.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    var editedIngredientIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
            Text(
                text = "Recognized ingredients",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(18.dp))
                    .background(Card, RoundedCornerShape(18.dp))
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Line)
                )

                LazyColumn {
                    itemsIndexed(ingredients) { index, ingredient ->
                        IngredientRow(
                            name = ingredient.name,
                            quantity = ingredient.amount?.formatAmount() ?: "",
                            unit = ingredient.unit?.name ?: "",
                            onClick = {
                                editedIngredientIndex = index
                            },
                            onRemove = {
                                viewModel.remove(index)
                            }
                        )

                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        if (viewModel.isValid()) onConfirm(ingredients)
                        else showErrorDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Confirm", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onRescan,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Primary
                    ),
                    border = BorderStroke(1.dp, Line)
                ) {
                    Text(text="Rescan",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                        )
                }
            }
        }
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },

            containerColor = Background,
            shape = RoundedCornerShape(24.dp),

            title = {
                Text(
                    text = "Missing data",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },

            text = {
                Text(
                    text = "Some ingredients are missing amount or unit. Please complete them.",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },

            confirmButton = {
                OutlinedButton(
                    onClick = { showErrorDialog = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Primary
                    ),
                    border = BorderStroke(1.dp, Line)
                ) {
                    Text(
                        text = "OK",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }

    editedIngredientIndex?.let { index ->
        IngredientBottomSheet(
            initialIngredient = ingredients[index],
            onSave = { updatedIngredient ->
                viewModel.update(index, updatedIngredient)
                editedIngredientIndex = null
            },
            onCancel = {
                editedIngredientIndex = null
            },
            onDismiss = {
                editedIngredientIndex = null
            }
        )
    }


}
