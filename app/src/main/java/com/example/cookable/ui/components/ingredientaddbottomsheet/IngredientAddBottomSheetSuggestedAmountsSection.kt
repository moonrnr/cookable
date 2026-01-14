package com.example.cookable.ui.components.ingredientaddbottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.components.chip.SelectableChip
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun SuggestedAmountsSection(
    amounts: List<Double>,
    onClick: (Double) -> Unit,
    onLongClick: (Double) -> Unit,
) {
    if (amounts.isEmpty()) return

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Suggested:",
            color = PrimaryGreen,
            fontSize = 13.sp,
            fontStyle = FontStyle.Italic,
        )

        Spacer(Modifier.width(10.dp))

        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            amounts.forEach { amount ->
                SelectableChip(
                    text = amount.toString(),
                    onClick = { onClick(amount) },
                    onLongClick = { onLongClick(amount) },
                )
            }
        }
    }
}
