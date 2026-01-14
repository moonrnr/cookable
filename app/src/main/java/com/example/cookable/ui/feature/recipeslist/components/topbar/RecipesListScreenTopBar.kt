package com.example.cookable.ui.feature.recipeslist.components.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookable.ui.components.iconbuttons.arrows.ArrowBackIconButton
import com.example.cookable.ui.components.screentitle.ScreenTitle
import com.example.cookable.ui.components.sortfiltercontainer.SortFilterContainer

@Composable
fun RecipesListScreenTopBar(
    onBackClick: () -> Unit,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    end = 20.dp,
                    top = 12.dp,
                    bottom = 10.dp,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ArrowBackIconButton(onClick = onBackClick)
        Spacer(modifier = Modifier.width(8.dp))
        ScreenTitle(text = "Recipes")
        Spacer(modifier = Modifier.width(30.dp))
        SortFilterContainer(
            onSortClick = onSortClick,
            onFilterClick = onFilterClick,
        )
    }
}
