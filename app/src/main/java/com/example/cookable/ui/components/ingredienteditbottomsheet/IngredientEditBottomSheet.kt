package com.example.cookable.ui.components.ingredienteditbottomsheet

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.model.IngredientBottomSheetType
import com.example.cookable.domain.model.UnitType
import com.example.cookable.ui.components.buttons.PrimaryButton
import com.example.cookable.ui.components.buttons.SecondaryButton
import com.example.cookable.ui.components.pill.Pill
import com.example.cookable.ui.theme.Background
import com.example.cookable.ui.theme.Grey
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientEditBottomSheet(
    initialIngredient: Ingredient = Ingredient(name = "", amount = null, unit = null),
    onSave: (Ingredient) -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
    ingredientBottomSheetType: IngredientBottomSheetType,
) {
    var name by remember { mutableStateOf(initialIngredient.name) }
    var amount by remember { mutableStateOf(initialIngredient.amount?.toString().orEmpty()) }
    var unit by remember { mutableStateOf(initialIngredient.unit) }
    var unitExpanded by remember { mutableStateOf(false) }

    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )

    val isAmountValid =
        amount.matches(Regex("""\d+(\.\d+)?"""))

    val canSave =
        name.isNotBlank() &&
            isAmountValid &&
            unit != null

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
                    .padding(0.dp)
                    .navigationBarsPadding(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 18.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text =
                        when (ingredientBottomSheetType) {
                            IngredientBottomSheetType.ADD -> "Add ingredient"
                            IngredientBottomSheetType.EDIT -> "Edit ingredient"
                        },
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

            Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 24.dp)) {
                Text("Ingredient name", fontSize = 13.sp)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g., milk", fontStyle = FontStyle.Italic) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Line,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = PrimaryGreen,
                            focusedPlaceholderColor = Grey,
                            unfocusedPlaceholderColor = Grey,
                        ),
                )

                Spacer(Modifier.height(12.dp))

                Text("Amount", fontSize = 13.sp)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newValue ->
                        if (
                            newValue.isEmpty() ||
                            newValue.matches(Regex("""\d*\.?\d*"""))
                        ) {
                            amount = newValue
                        }
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                    isError = amount.isNotBlank() && !isAmountValid,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g., 2", fontStyle = FontStyle.Italic) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Line,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = PrimaryGreen,
                            focusedPlaceholderColor = Grey,
                            unfocusedPlaceholderColor = Grey,
                        ),
                )

                if (amount.isNotBlank() && !isAmountValid) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Enter a valid number (e.g. 2 or 2.5)",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                    )
                }

                initialIngredient.amountSuggestion?.let {
                    Spacer(Modifier.height(6.dp))
                    Pill(
                        backgroundColor = Color(0x3366BB6A),
                        onClick = { amount = it.toString() },
                    ) {
                        Text(
                            text = "suggested: $it",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            color = PrimaryGreen,
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text("Unit", fontSize = 13.sp)
                Spacer(Modifier.height(6.dp))

                ExposedDropdownMenuBox(
                    expanded = unitExpanded,
                    onExpandedChange = { unitExpanded = !unitExpanded },
                ) {
                    OutlinedTextField(
                        value = unit?.name?.lowercase() ?: "",
                        onValueChange = {},
                        readOnly = true,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitExpanded)
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = Line,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                cursorColor = PrimaryGreen,
                            ),
                    )

                    ExposedDropdownMenu(
                        expanded = unitExpanded,
                        onDismissRequest = { unitExpanded = false },
                        modifier =
                            Modifier
                                .background(Background, RoundedCornerShape(14.dp)),
                    ) {
                        UnitType.values().forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = it.fullLabel.lowercase(),
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                },
                                onClick = {
                                    unit = it
                                    unitExpanded = false
                                },
                                colors =
                                    MenuDefaults.itemColors(
                                        textColor = MaterialTheme.colorScheme.onBackground,
                                    ),
                            )
                        }
                    }
                }

                initialIngredient.unitSuggestion?.let {
                    Spacer(Modifier.height(6.dp))
                    Pill(
                        backgroundColor = Color(0x3366BB6A),
                        onClick = { unit = it },
                    ) {
                        Text(
                            text = "suggested: ${it.name.lowercase()}",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            color = PrimaryGreen,
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    SecondaryButton(
                        text = "Cancel",
                        onClick = onCancel,
                        modifier =
                            Modifier
                                .weight(1f),
                    )
                    PrimaryButton(
                        text = "Save",
                        onClick = {
                            onSave(
                                Ingredient(
                                    name = name,
                                    amount = amount.toDoubleOrNull(),
                                    unit = unit,
                                ),
                            )
                        },
                        enabled = canSave,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}
