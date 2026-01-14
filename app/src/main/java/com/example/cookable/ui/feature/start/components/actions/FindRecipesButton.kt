package com.example.cookable.ui.feature.start.components.actions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.PrimaryOrange

@Composable
fun FindRecipesButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = PrimaryOrange,
                contentColor = Color.White,
            ),
        shape = RoundedCornerShape(18.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp)
                .height(54.dp),
    ) {
        Text(
            text = "Find recipes",
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}
