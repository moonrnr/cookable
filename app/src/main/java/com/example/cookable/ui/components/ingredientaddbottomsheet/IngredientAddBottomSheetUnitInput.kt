package com.example.cookable.ui.components.ingredientaddbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.domain.model.UnitType
import com.example.cookable.ui.theme.Background
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientUnitInput(
    selectedUnit: UnitType?,
    onUnitSelected: (UnitType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text("Unit", fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                value = selectedUnit?.name?.lowercase() ?: "",
                onValueChange = {},
                readOnly = true,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
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
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier =
                    Modifier.background(
                        Background,
                        RoundedCornerShape(14.dp),
                    ),
            ) {
                UnitType.values().forEach { unit ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = unit.fullLabel.lowercase(),
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        },
                        onClick = {
                            onUnitSelected(unit)
                            expanded = false
                        },
                        colors =
                            MenuDefaults.itemColors(
                                textColor = MaterialTheme.colorScheme.onBackground,
                            ),
                    )
                }
            }
        }
    }
}
