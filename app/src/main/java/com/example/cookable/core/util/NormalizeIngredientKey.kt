package com.example.cookable.core.util

fun normalizeIngredientKey(input: String): String =
    input
        .lowercase()
        .replace(Regex("[^a-z ]"), "")
        .replace(Regex("\\s+"), " ")
        .trim()
