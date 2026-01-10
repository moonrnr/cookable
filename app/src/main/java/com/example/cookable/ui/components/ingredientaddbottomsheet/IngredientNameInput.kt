package com.example.cookable.ui.components.ingredientaddbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.Grey
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun IngredientNameInput(
    value: String,
    suggestions: List<String>,
    onValueChange: (String) -> Unit,
    onSuggestionSelected: (String) -> Unit,
    onSuggestionLongPressed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text("Ingredient name", fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("e.g., milk", fontStyle = FontStyle.Italic) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth(),
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

        if (suggestions.isNotEmpty()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(18.dp),
                            clip = false,
                        ).background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp),
                        ).border(
                            width = 1.dp,
                            color = Line,
                            shape = RoundedCornerShape(12.dp),
                        ),
            ) {
                suggestions
                    .take(5)
                    .forEachIndexed { index, suggestion ->
                        Text(
                            text = suggestion,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        onClick = {
                                            onSuggestionSelected(suggestion)
                                        },
                                        onLongClick = {
                                            onSuggestionLongPressed(suggestion)
                                        },
                                    ).padding(horizontal = 12.dp, vertical = 10.dp),
                            fontSize = 14.sp,
                        )

                        if (index < suggestions.lastIndex) {
                            HorizontalDivider(
                                color = Line.copy(alpha = 0.4f),
                            )
                        }
                    }
            }
        }
    }
}
