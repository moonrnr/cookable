package com.example.cookable.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.domain.model.Status
import com.example.cookable.ui.theme.Primary
import com.example.cookable.ui.theme.PrimaryOrange

@Composable
fun StatusBox(
    status: Status,
    text: String,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, textColor, icon) =
        when (status) {
            Status.SUCCESS ->
                Triple(
                    Primary.copy(alpha = 0.12f),
                    Primary,
                    Icons.Filled.Done,
                )

            Status.WARNING ->
                Triple(
                    PrimaryOrange.copy(alpha = 0.14f),
                    PrimaryOrange,
                    Icons.Outlined.Warning,
                )

            Status.ERROR ->
                Triple(
                    Color.Red.copy(alpha = 0.12f),
                    Color.Red,
                    Icons.Outlined.Clear,
                )
        }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(14.dp))
                .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.size(16.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )
    }
}
