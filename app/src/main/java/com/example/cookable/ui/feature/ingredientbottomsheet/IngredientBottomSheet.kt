package com.example.cookable.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.domain.model.Ingredient
import com.example.cookable.domain.model.UnitType
import com.example.cookable.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientBottomSheet(
    initialIngredient: Ingredient = Ingredient(name = "", amount = null, unit = null),
    suggestedAmount: Double? = null,
    suggestedUnit: UnitType? = null,
    onSave: (Ingredient) -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(initialIngredient.name) }
    var amount by remember { mutableStateOf(initialIngredient.amount?.toString().orEmpty()) }
    var unit by remember { mutableStateOf(initialIngredient.unit) }
    var unitExpanded by remember { mutableStateOf(false) }

    val canSave =
        name.isNotBlank() &&
                amount.isNotBlank() &&
                unit != null

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(width = 40.dp, height = 4.dp)
                    .background(Color.LightGray, RoundedCornerShape(2.dp))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add ingredient",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "×",
                    fontSize = 22.sp,
                    modifier = Modifier.clickable { onDismiss() }
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Ingredient name", fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., milk", fontStyle = FontStyle.Italic) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                unfocusedBorderColor = Line,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Primary,
                        focusedPlaceholderColor = Grey,
                    unfocusedPlaceholderColor = Grey
            )
            )

            Spacer(Modifier.height(12.dp))

            Text("Amount", fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., 2", fontStyle = FontStyle.Italic) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Line,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Primary,
                    focusedPlaceholderColor = Grey,
                    unfocusedPlaceholderColor = Grey
                )
            )

            suggestedAmount?.let {
                Spacer(Modifier.height(6.dp))
                Pill(
                    backgroundColor = Color(0x3366BB6A),
                    onClick = { amount = it.toString() }
                ) {
                    Text(
                        text = "Suggested: $it",
                        fontSize = 12.sp,
                        color = Primary
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text("Unit", fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))

            ExposedDropdownMenuBox(
                expanded = unitExpanded,
                onExpandedChange = { unitExpanded = !unitExpanded }
            ) {
                OutlinedTextField(
                    value = unit?.name?.lowercase() ?: "",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitExpanded)
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = Line,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Primary
                    )
                )

                ExposedDropdownMenu(
                    expanded = unitExpanded,
                    onDismissRequest = { unitExpanded = false },
                    modifier = Modifier
                        .background(Background, RoundedCornerShape(14.dp))
                ) {
                    UnitType.values().forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it.name.lowercase(),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            onClick = {
                                unit = it
                                unitExpanded = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }

            }

            suggestedUnit?.let {
                Spacer(Modifier.height(6.dp))
                Pill(
                    backgroundColor = Color(0x3366BB6A),
                    onClick = { unit = it }
                ) {
                    Text(
                        text = "Suggested: ${it.name.lowercase()}",
                        fontSize = 12.sp,
                        color = Primary
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Primary
                    ),
                    border = BorderStroke(1.dp, Line)
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        onSave(
                            Ingredient(
                                name = name,
                                amount = amount.toDoubleOrNull(),
                                unit = unit
                            )
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    enabled = canSave,
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        disabledContainerColor = Color(0xFFDADCE0),
                        contentColor = Color.White,
                        disabledContentColor = Color(0xFF9AA0A6)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    )
                ) {
                    Text(
                        text = "Save",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}
