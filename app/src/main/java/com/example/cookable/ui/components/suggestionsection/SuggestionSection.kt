package com.example.cookable.ui.components.suggestionsection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.Grey

@Composable
private fun SuggestionSection(
    title: String = "suggested",
    content: @Composable () -> Unit,
) {
    Column {
        Spacer(Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            color = Grey,
        )
        Spacer(Modifier.height(6.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            content()
        }
    }
}
