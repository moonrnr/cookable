package com.example.cookable.ui.components.ingredientaddbottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen
import com.example.cookable.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientAddBottomSheet(
    viewModel: IngredientAddBottomSheetViewModel,
    onSave: (Ingredient) -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = {
            Box(
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .size(width = 40.dp, height = 4.dp)
                        .background(Color.LightGray, RoundedCornerShape(2.dp)),
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .navigationBarsPadding(),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Add ingredient",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(end = 10.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = Line)
            Spacer(Modifier.height(12.dp))

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            ) {
                IngredientNameInput(
                    value = state.name,
                    suggestions = state.nameSuggestions,
                    onValueChange = viewModel::onNameChanged,
                    onSuggestionSelected = viewModel::onNameSuggestionClicked,
                    onSuggestionLongPressed = viewModel::onNameSuggestionLongPressed,
                )

                Spacer(Modifier.height(12.dp))

                IngredientAmountInput(
                    value = state.amount,
                    onValueChange = viewModel::onAmountChanged,
                )
                Spacer(Modifier.height(6.dp))
                SuggestedAmountsSection(
                    amounts = state.suggestedAmounts,
                    onClick = viewModel::onSuggestedAmountClicked,
                    onLongClick = viewModel::onSuggestedAmountLongPressed,
                )

                Spacer(Modifier.height(12.dp))

                IngredientUnitInput(
                    selectedUnit = state.unit,
                    onUnitSelected = viewModel::onUnitChanged,
                )
                Spacer(Modifier.height(6.dp))
                SuggestedUnitsSection(
                    units = state.suggestedUnits,
                    onClick = viewModel::onSuggestedUnitClicked,
                    onLongClick = viewModel::onSuggestedUnitLongPressed,
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedButton(
                        onClick = onCancel,
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(52.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors =
                            ButtonDefaults.outlinedButtonColors(
                                contentColor = PrimaryGreen,
                            ),
                        border = BorderStroke(1.dp, Line),
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.onSaveClicked { ingredient ->
                                onSave(ingredient)
                            }
                        },
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(52.dp),
                        enabled = state.isSaveEnabled,
                        shape = RoundedCornerShape(18.dp),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = PrimaryGreen,
                                disabledContainerColor = Color(0xFFDADCE0),
                                contentColor = Color.White,
                                disabledContentColor = Color(0xFF9AA0A6),
                            ),
                        elevation =
                            ButtonDefaults.buttonElevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp,
                            ),
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                }
            }
        }
    }
    state.deleteSuggestion?.let {
        AlertDialog(
            containerColor = White,
            onDismissRequest = viewModel::cancelDeleteSuggestion,
            title = {
                Text("Remove suggestion")
            },
            text = {
                Text("Do you want to remove this suggestion?")
            },
            confirmButton = {
                Button(
                    onClick = viewModel::confirmDeleteSuggestion,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                ) {
                    Text("Remove", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = viewModel::cancelDeleteSuggestion,
                ) {
                    Text("Cancel")
                }
            },
        )
    }
}
