package com.example.cookable.core.util

import com.example.cookable.domain.model.Recipe

fun mergeRecipesLists(
    first: List<Recipe>,
    second: List<Recipe>,
): List<Recipe> =
    (first + second)
        .associateBy { it.id }
        .values
        .toList()
