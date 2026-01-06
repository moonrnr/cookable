package com.example.cookable.ui.components.filterbottomsheet

data class FilterBottomSheetState(
    val vegetarian: Boolean = false,
    val vegan: Boolean = false,
    val glutenFree: Boolean = false,
    val keto: Boolean = false,
    val breakfast: Boolean = false,
    val lunch: Boolean = false,
    val dinner: Boolean = false,
    val supper: Boolean = false,
    val difficultyEasy: Boolean = false,
    val difficultyMedium: Boolean = false,
    val difficultyHard: Boolean = false,
    val minTime: String = "",
    val maxTime: String = "",
) {
    val isEmpty: Boolean
        get() =
            !vegetarian &&
                !vegan &&
                !glutenFree &&
                !keto &&
                !breakfast &&
                !lunch &&
                !dinner &&
                !supper &&
                !difficultyEasy &&
                !difficultyMedium &&
                !difficultyHard &&
                minTime.isBlank() &&
                maxTime.isBlank()
}
