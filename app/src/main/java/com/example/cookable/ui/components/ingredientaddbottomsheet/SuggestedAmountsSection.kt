package com.example.cookable.ui.components.ingredientaddbottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.PrimaryGreen
import com.example.cookable.ui.theme.PrimaryGreenLight

@Composable
fun SuggestedAmountsSection(
    amounts: List<Double>,
    onClick: (Double) -> Unit,
) {
    if (amounts.isEmpty()) return

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Suggested:",
            color = PrimaryGreen,
            fontSize = 13.sp,
            fontStyle = FontStyle.Italic,
        )

        Spacer(modifier = Modifier.width(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            amounts.forEach { amount ->
                AssistChip(
                    onClick = { onClick(amount) },
                    label = {
                        Text(
                            text = amount.toString(),
                            color = PrimaryGreen,
                            fontSize = 12.sp,
                        )
                    },
                    colors =
                        AssistChipDefaults.assistChipColors(
                            containerColor = PrimaryGreenLight,
                        ),
                    shape = RoundedCornerShape(50),
                )
            }
        }
    }
}
