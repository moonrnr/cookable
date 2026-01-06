package com.example.cookable.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.R
import com.example.cookable.ui.theme.Muted

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.cookable_logo),
                contentDescription = "Cookable logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp).padding(bottom = 4.dp),
            )
            Text(
                text = "Cookable",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic,
            )
        }
        Text(
            text = "Cook with what you have",
            fontSize = 13.sp,
            color = Muted,
            modifier = Modifier.padding(start = 32.dp),
        )
    }
}
