package com.example.cookable.domain.model

enum class UnitType(
    val shortLabel: String,
    val fullLabel: String,
) {
    GRAM("g", "gram"),
    KILOGRAM("kg", "kilogram"),
    MILLILITER("ml", "milliliter"),
    LITER("l", "liter"),
    PIECE("pcs", "piece"),
    POUND("lb", "pound"),
    TABLESPOON("tbsp", "tablespoon"),
}
