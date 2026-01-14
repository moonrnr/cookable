package com.example.cookable.ui.feature.recognizedingredients.components.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.ui.components.iconbuttons.arrows.ArrowBackIconButton
import com.example.cookable.ui.components.ingredientscountbadge.IngredientsCountBadge
import com.example.cookable.ui.components.screentitle.ScreenTitle
import com.example.cookable.ui.theme.PrimaryGreen
import com.example.cookable.ui.theme.White

@Composable
fun RecognizedIngredientsTopbar(
    ingredientsCount: Int,
    onBack: () -> Unit,
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
        ArrowBackIconButton(onBack)
        Spacer(modifier = Modifier.width(8.dp))
        ScreenTitle(text = "Recognized ingredients")
        Spacer(modifier = Modifier.width(16.dp))
        IngredientsCountBadge(
            ingredientsCount = ingredientsCount,
            backgroundColor = PrimaryGreen,
            textColor = White,
            isTextBold = true,
            fontSize = 14.sp,
        )
    }
}
