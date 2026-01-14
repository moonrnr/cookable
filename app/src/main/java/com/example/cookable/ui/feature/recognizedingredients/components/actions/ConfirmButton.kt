package com.example.cookable.ui.feature.recognizedingredients.components.actions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cookable.ui.theme.PrimaryGreen
import com.example.cookable.ui.theme.White

@Composable
fun ConfirmButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = White,
            ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth().height(50.dp),
    ) {
        Text("Confirm", fontWeight = FontWeight.Bold)
    }
}
