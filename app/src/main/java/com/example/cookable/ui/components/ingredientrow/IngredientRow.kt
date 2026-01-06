package com.example.cookable.ui.components.ingredientrow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.domain.model.UnitType
import com.example.cookable.ui.theme.PrimaryGreen
import com.example.cookable.ui.theme.PrimaryGreenLight
import com.example.cookable.ui.theme.Red

@Composable
fun IngredientRow(
    name: String,
    amount: String,
    hasError: Boolean = false,
    unit: UnitType?,
    suggestedAmount: String? = null,
    suggestedUnit: UnitType? = null,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(
                    if (hasError) {
                        Modifier.drawBehind {
                            val stroke = 12.dp.toPx()
                            val h = size.height

                            drawLine(Red, Offset(0f, 0f), Offset(0f, h), stroke)
                            drawLine(Red, Offset(size.width, 0f), Offset(size.width, h), stroke)
                        }
                    } else {
                        Modifier
                    },
                ).padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier =
                Modifier
                    .weight(1f)
                    .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = name,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
            )

            if (amount.isNotBlank()) {
                Box(
                    modifier =
                        Modifier
                            .background(
                                color = Color(0xFFF1F5F1),
                                shape = RoundedCornerShape(999.dp),
                            ).padding(horizontal = 12.dp, vertical = 6.dp),
                ) {
                    Text(
                        text = "$amount ${unit?.shortLabel}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            } else if (suggestedAmount != null && suggestedUnit != null) {
                Box(
                    modifier =
                        Modifier
                            .background(
                                color = PrimaryGreenLight,
                                shape = RoundedCornerShape(999.dp),
                            ).padding(horizontal = 12.dp, vertical = 6.dp),
                ) {
                    Text(
                        text = "suggested: $suggestedAmount ${suggestedUnit.shortLabel}",
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = PrimaryGreen,
                    )
                }
            }
        }

        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Red,
            )
        }
    }
}
