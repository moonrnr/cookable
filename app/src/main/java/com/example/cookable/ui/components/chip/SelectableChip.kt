package com.example.cookable.ui.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun SelectableChip(
    text: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .background(
                    Color(0xFF66BB6A).copy(alpha = 0.14f),
                    RoundedCornerShape(999.dp),
                ).combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick,
                ).padding(
                    horizontal = 10.dp,
                    vertical = 4.dp,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
        )
    }
}
