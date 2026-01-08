package com.example.cookable.ui.components.apphelp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.theme.White

@Composable
fun AppHelp(
    isVisible: Boolean,
    onDismiss: () -> Unit,
) {
    if (!isVisible) return

    AlertDialog(
        containerColor = White,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Got it",
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        title = {
            Text(
                text = "How the App Works",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "Discover recipes using the ingredients you already have!",
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text =
                        "Scan your ingredients with your camera or add them manually. " +
                            "Our app recognizes products and creates a smart list of recipes that match your ingredients.",
                )

                Text(
                    text =
                        "Review the recognized ingredients, adjust quantities or units, " +
                            "and add anything that wasn’t detected correctly.",
                )

                Text(
                    text =
                        "Find recipes ranked by match level:\n" +
                            "• PERFECT – all ingredients match\n" +
                            "• HIGH – missing a few ingredient\n" +
                            "• MEDIUM – missing several ingredients\n" +
                            "• LOW – missing a lot ingredients",
                )

                Text(
                    text = "Filter recipes, open details, and follow step-by-step cooking instructions.",
                )
            }
        },
        shape = RoundedCornerShape(20.dp),
    )
}
