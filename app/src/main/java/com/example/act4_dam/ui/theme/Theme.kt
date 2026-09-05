package com.example.act4_dam.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD6AD87),
    onPrimary = Color(0xFF3B281C),
    background = Color(0xFFFAF5EF),
    onBackground = Color(0xFFF7F1EA),
    surface = Color(0xFF2C231D),
    onSurface = Color(0xFFF7F1EA),
    onSurfaceVariant = Color(0xFFC5B3A3),
    outline = Color(0xFF5A4738)
)


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6F4E37),
    onPrimary = Color.White,
    background = Color(0xFFF7F1EA),
    onBackground = Color(0xFF2B2118),
    surface = Color(0xFFFFFDFC),
    onSurface = Color(0xFF2B2118),
    onSurfaceVariant = Color(0xFF806F61),
    outline = Color(0xFFDCCBBB)
)


@Composable
fun ACT4_DAMTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
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
        content = content
    )
}