package com.example.cookable.ui.feature.bottomsheets.filterbottomsheet

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
    val matchPerfect: Boolean = false,
    val matchHigh: Boolean = false,
    val matchMedium: Boolean = false,
    val matchLow: Boolean = false,
    val tagQuery: String = "",
    val selectedTags: List<String> = emptyList(),
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
                maxTime.isBlank() &&
                !matchPerfect &&
                !matchHigh &&
                !matchMedium &&
                !matchLow &&
                selectedTags.isEmpty()

    companion object {
        fun empty(): FilterBottomSheetState = FilterBottomSheetState()
    }
}
