package com.example.cookable.ui.feature.recognizedingredients.components.actions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun RescanButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth().height(50.dp),
        colors =
            ButtonDefaults.outlinedButtonColors(
                contentColor = PrimaryGreen,
            ),
        border = BorderStroke(1.dp, Line),
    ) {
        Text(
            text = "Rescan",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
