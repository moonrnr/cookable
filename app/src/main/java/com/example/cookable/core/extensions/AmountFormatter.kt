package com.example.cookable.core.extensions

fun Double.formatAmount(): String =
    if (this == this.toLong().toDouble())
        this.toLong().toString()
    else
        this.toString()
            .trimEnd('0')
            .trimEnd('.')
