package com.example.cookable.ui.feature.recognizedingredients.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cookable.ui.theme.Background
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun MissingDataModal(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Background,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Missing data",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        text = {
            Text(
                text = "Some ingredients are missing amount or unit. Please complete them.",
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        confirmButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                shape = RoundedCornerShape(18.dp),
                colors =
                    ButtonDefaults.outlinedButtonColors(
                        contentColor = PrimaryGreen,
                    ),
                border = BorderStroke(1.dp, Line),
            ) {
                Text(
                    text = "OK",
                    fontWeight = FontWeight.Bold,
                )
            }
        },
    )
}
