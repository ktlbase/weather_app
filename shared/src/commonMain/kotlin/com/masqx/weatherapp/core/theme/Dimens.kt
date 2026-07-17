package com.masqx.weatherapp.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** design.md `rounded` scale. */
data class AppShapes(
    val sm: RoundedCornerShape = RoundedCornerShape(4.dp),
    val default: RoundedCornerShape = RoundedCornerShape(8.dp),
    val md: RoundedCornerShape = RoundedCornerShape(12.dp),
    val lg: RoundedCornerShape = RoundedCornerShape(16.dp),
    val xl: RoundedCornerShape = RoundedCornerShape(24.dp),
    val full: RoundedCornerShape = RoundedCornerShape(percent = 50),
)

/** design.md `spacing` scale, 4px unit. */
data class AppSpacing(
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 48.dp,
    val gutter: Dp = 16.dp,
)

val LocalAppShapes = staticCompositionLocalOf { AppShapes() }
val LocalAppSpacing = staticCompositionLocalOf { AppSpacing() }
