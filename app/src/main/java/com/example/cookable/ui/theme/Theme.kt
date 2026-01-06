package com.example.cookable.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
    darkColorScheme(
        primary = Secondary,
        onPrimary = Black,
        secondary = PrimaryGreenLight,
        onSecondary = Black,
        background = Black,
        onBackground = White,
        surface = DarkGrey,
        onSurface = White,
        outline = Grey,
        error = Red,
        onError = White,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = PrimaryGreen,
        onPrimary = White,
        secondary = Secondary,
        onSecondary = PrimaryGreen,
        tertiary = PrimaryOrange,
        onTertiary = White,
        background = Background,
        onBackground = DarkGrey,
        surface = Card,
        onSurface = DarkGrey,
        outline = Line,
        outlineVariant = LightGrey,
        error = Red,
        onError = White,
    )

@Composable
fun CookableTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
