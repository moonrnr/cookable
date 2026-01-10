package com.example.cookable.ui.components.ingredientaddbottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.Grey
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun IngredientAmountInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isAmountValid =
        value.matches(Regex("""\d+(\.\d+)?"""))

    Column(modifier = modifier) {
        Text("Amount", fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                val parts = newValue.split(".")
                val integerPart = parts.getOrNull(0).orEmpty()
                val decimalPart = parts.getOrNull(1)
                val isValid =
                    integerPart.length <= 6 &&
                        (decimalPart?.length ?: 0) <= 2 &&
                        newValue.matches(Regex("""\d*\.?\d*"""))

                if (newValue.isEmpty() || isValid) {
                    onValueChange(newValue)
                }
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
            isError = value.isNotBlank() && !isAmountValid,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "e.g., 2",
                    fontStyle = FontStyle.Italic,
                )
            },
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

        if (value.isNotBlank() && !isAmountValid) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Enter a valid number (e.g. 2 or 2.5)",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
            )
        }
    }
}
