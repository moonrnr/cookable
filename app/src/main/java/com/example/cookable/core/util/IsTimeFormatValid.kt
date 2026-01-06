package com.example.cookable.core.util

fun isTimeFormatValid(value: String): Boolean {
    if (value.isBlank()) return true
    return value.matches(Regex("""^\d+([.,]\d+)?$"""))
}
