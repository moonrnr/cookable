package com.example.cookable.ui.components.sortfiltercontainer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SortFilterContainer(
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onSortClick) {
            Icon(
                imageVector = Icons.Rounded.SwapVert,
                contentDescription = "Sort",
            )
        }
        IconButton(onClick = onFilterClick) {
            Icon(
                imageVector = Icons.Rounded.FilterAlt,
                contentDescription = "Filter",
            )
        }
    }
}
