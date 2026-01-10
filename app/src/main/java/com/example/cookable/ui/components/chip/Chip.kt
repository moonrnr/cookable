package com.example.cookable.ui.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
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
fun Chip(
    text: String,
    onRemove: (() -> Unit)? = null,
) {
    Row(
        modifier =
            Modifier
                .background(
                    Color(0xFF66BB6A).copy(alpha = 0.14f),
                    RoundedCornerShape(999.dp),
                ).padding(
                    start = 10.dp,
                    end = if (onRemove != null) 6.dp else 10.dp,
                    top = 4.dp,
                    bottom = 4.dp,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
        )
        onRemove?.let {
            Spacer(Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = PrimaryGreen,
                modifier =
                    Modifier
                        .size(20.dp)
                        .clickable { it() },
            )
        }
    }
}
