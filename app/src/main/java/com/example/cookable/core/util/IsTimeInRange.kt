package com.example.cookable.core.util

fun isTimeInRange(value: String): Boolean {
    if (value.isBlank()) return true

    val number =
        value
            .replace(',', '.')
            .toDoubleOrNull()
            ?: return false

    return number in 1.0..1440.0
}
