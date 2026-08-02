package com.example.lgsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LgsLightColors = lightColorScheme(
    primary = Color(0xFF1F3A5F),
    secondary = Color(0xFF2E9E6D),
    background = Color(0xFFF5F0E8),
    surface = Color(0xFFFFFFFF)
)

@Composable
fun LgsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LgsLightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
