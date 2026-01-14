package com.example.cookable.ui.feature.bottomsheets.filterbottomsheet.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.components.chip.Chip
import com.example.cookable.ui.theme.Grey
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen

@Composable
fun TagFilterInput(
    allTags: List<String>,
    query: String,
    selectedTags: List<String>,
    onQueryChange: (String) -> Unit,
    onTagSelected: (String) -> Unit,
    onTagRemoved: (String) -> Unit,
) {
    val filteredTags =
        remember(query, selectedTags) {
            if (query.isBlank()) {
                emptyList()
            } else {
                allTags
                    .filter { it.contains(query, ignoreCase = true) }
                    .filterNot { it in selectedTags }
            }
        }

    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Type tag name…") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = Line,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = PrimaryGreen,
                    focusedPlaceholderColor = Grey,
                    unfocusedPlaceholderColor = Grey,
                ),
        )

        if (filteredTags.isNotEmpty()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(18.dp),
                            clip = false,
                        ).background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp),
                        ).border(
                            width = 1.dp,
                            color = Line,
                            shape = RoundedCornerShape(12.dp),
                        ),
            ) {
                filteredTags.take(5).forEachIndexed { index, tag ->
                    Text(
                        text = tag,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { onTagSelected(tag) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                        fontSize = 14.sp,
                    )

                    if (index < filteredTags.lastIndex) {
                        HorizontalDivider(color = Line.copy(alpha = 0.4f))
                    }
                }
            }
        }

        if (selectedTags.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                selectedTags.forEach { tag ->
                    Chip(
                        text = tag,
                        onRemove = { onTagRemoved(tag) },
                    )
                }
            }
        }
    }
}
