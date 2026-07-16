package com.masqx.weatherapp.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

/** "Atmospheric Clarity Light" — design.md palette, mapped 1:1 to Material3 ColorScheme. */
val SeedColor = Color(0xFF4648D4)

private val AtmosphericClarityLightColorScheme = lightColorScheme(
    primary = Color(0xFF4648D4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE1E0FF),
    onPrimaryContainer = Color(0xFF07006C),
    inversePrimary = Color(0xFFC0C1FF),
    secondary = Color(0xFF006591),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFC9E6FF),
    onSecondaryContainer = Color(0xFF001E2F),
    tertiary = Color(0xFFB90538),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDADB),
    onTertiaryContainer = Color(0xFF40000D),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF93000A),
    // Из мокапа: страница #EEF1FA, карточки (surface) — чистый белый поверх неё.
    background = Color(0xFFEEF1FA),
    onBackground = Color(0xFF0B1C30),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0B1C30),
    surfaceVariant = Color(0xFFE5EEFF),
    onSurfaceVariant = Color(0xFF464554),
    outline = Color(0xFF767586),
    outlineVariant = Color(0xFFC7C4D7),
    surfaceTint = Color(0xFF494BD6),
    inverseSurface = Color(0xFF213145),
    inverseOnSurface = Color(0xFFEAF1FF),
    surfaceDim = Color(0xFFCBDBF5),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF5F7FC),
    surfaceContainer = Color(0xFFEEF1FA),
    surfaceContainerHigh = Color(0xFFE4E8F5),
    surfaceContainerHighest = Color(0xFFD9DFF0),
    primaryFixed = Color(0xFFE1E0FF),
    primaryFixedDim = Color(0xFFC0C1FF),
    onPrimaryFixed = Color(0xFF07006C),
    onPrimaryFixedVariant = Color(0xFF2F2EBE),
    secondaryFixed = Color(0xFFC9E6FF),
    secondaryFixedDim = Color(0xFF89CEFF),
    onSecondaryFixed = Color(0xFF001E2F),
    onSecondaryFixedVariant = Color(0xFF004C6E),
    tertiaryFixed = Color(0xFFFFDADB),
    tertiaryFixedDim = Color(0xFFFFB2B7),
    onTertiaryFixed = Color(0xFF40000D),
    onTertiaryFixedVariant = Color(0xFF92002A),
)

/**
 * "Atmospheric Clarity Dark" — same role logic as light, inverted for dark surfaces.
 * Primary/secondary/tertiary lightened (Material dark-theme rule: 40% tone, not 100%)
 * so they stay legible without glaring on near-black backgrounds.
 */
private val AtmosphericClarityDarkColorScheme = darkColorScheme(
    primary = Color(0xFFC0C1FF),
    onPrimary = Color(0xFF2F2EBE),
    primaryContainer = Color(0xFF2F2EBE),
    onPrimaryContainer = Color(0xFFE1E0FF),
    inversePrimary = Color(0xFF4648D4),
    secondary = Color(0xFF89CEFF),
    onSecondary = Color(0xFF00344C),
    secondaryContainer = Color(0xFF004C6E),
    onSecondaryContainer = Color(0xFFC9E6FF),
    tertiary = Color(0xFFFFB2B7),
    onTertiary = Color(0xFF66000F),
    tertiaryContainer = Color(0xFF92002A),
    onTertiaryContainer = Color(0xFFFFDADB),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    // Зеркало light-схемы: страница темнее карточек (surface), а не одного цвета с ними —
    // сохраняет ту же "страница ниже, карточка выше" логику мокапа, инвертированную под тёмный фон.
    background = Color(0xFF0B1220),
    onBackground = Color(0xFFDCE4F9),
    surface = Color(0xFF1A2233),
    onSurface = Color(0xFFDCE4F9),
    surfaceVariant = Color(0xFF464554),
    onSurfaceVariant = Color(0xFFC7C4D7),
    outline = Color(0xFF908F9F),
    outlineVariant = Color(0xFF464554),
    surfaceTint = Color(0xFFC0C1FF),
    inverseSurface = Color(0xFFDCE4F9),
    inverseOnSurface = Color(0xFF213145),
    surfaceDim = Color(0xFF0B1220),
    surfaceBright = Color(0xFF31384A),
    surfaceContainerLowest = Color(0xFF060B14),
    surfaceContainerLow = Color(0xFF131A28),
    surfaceContainer = Color(0xFF1A2233),
    surfaceContainerHigh = Color(0xFF212A3C),
    surfaceContainerHighest = Color(0xFF2C3547),
    primaryFixed = Color(0xFFE1E0FF),
    primaryFixedDim = Color(0xFFC0C1FF),
    onPrimaryFixed = Color(0xFF07006C),
    onPrimaryFixedVariant = Color(0xFF2F2EBE),
    secondaryFixed = Color(0xFFC9E6FF),
    secondaryFixedDim = Color(0xFF89CEFF),
    onSecondaryFixed = Color(0xFF001E2F),
    onSecondaryFixedVariant = Color(0xFF004C6E),
    tertiaryFixed = Color(0xFFFFDADB),
    tertiaryFixedDim = Color(0xFFFFB2B7),
    onTertiaryFixed = Color(0xFF40000D),
    onTertiaryFixedVariant = Color(0xFF92002A),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) {
        AtmosphericClarityDarkColorScheme
    } else {
        AtmosphericClarityLightColorScheme
    }

    CompositionLocalProvider(
        LocalAppShapes provides AppShapes(),
        LocalAppSpacing provides AppSpacing(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = appTypography(),
            content = content,
        )
    }
}

object AppThemeTokens {
    val shapes: AppShapes
        @Composable get() = LocalAppShapes.current

    val spacing: AppSpacing
        @Composable get() = LocalAppSpacing.current
}