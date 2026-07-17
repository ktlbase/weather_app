package com.masqx.weatherapp.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import weatherapp.shared.generated.resources.Res
import weatherapp.shared.generated.resources.inter_bold
import weatherapp.shared.generated.resources.inter_light
import weatherapp.shared.generated.resources.inter_medium
import weatherapp.shared.generated.resources.inter_regular
import weatherapp.shared.generated.resources.inter_semibold
import weatherapp.shared.generated.resources.manrope_bold
import weatherapp.shared.generated.resources.manrope_extrabold
import weatherapp.shared.generated.resources.manrope_medium
import weatherapp.shared.generated.resources.manrope_regular
import weatherapp.shared.generated.resources.manrope_semibold
import org.jetbrains.compose.resources.Font

/**
 * Headlines and large temperature displays (design.md: "modern, technical, yet balanced").
 * Two-family system only (Manrope + Inter) — JetBrains Mono dropped from active use to avoid
 * a third visually distinct grotesque competing with body text in small labels.
 */
@Composable
fun manropeFontFamily(): FontFamily = FontFamily(
    Font(Res.font.manrope_regular, weight = FontWeight.Normal),
    Font(Res.font.manrope_medium, weight = FontWeight.Medium),
    Font(Res.font.manrope_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.manrope_bold, weight = FontWeight.Bold),
    Font(Res.font.manrope_extrabold, weight = FontWeight.ExtraBold),
)

/** Body text and descriptions (design.md: "exceptional legibility and systematic feel"). */
@Composable
fun interFontFamily(): FontFamily = FontFamily(
    Font(Res.font.inter_light, weight = FontWeight.Light),
    Font(Res.font.inter_regular, weight = FontWeight.Normal),
    Font(Res.font.inter_medium, weight = FontWeight.Medium),
    Font(Res.font.inter_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.inter_bold, weight = FontWeight.Bold),
)

/** design.md `display-xl`: 72sp / weight 800 / -0.04em / lineHeight 80sp. */
@Composable
fun displayXlStyle(): TextStyle = TextStyle(
    fontFamily = manropeFontFamily(),
    fontSize = 72.sp,
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 80.sp,
    letterSpacing = (-0.04).em,
)

/** design.md `display-xl-mobile`: 48sp / weight 800 / -0.04em / lineHeight 56sp. */
@Composable
fun displayXlMobileStyle(): TextStyle = TextStyle(
    fontFamily = manropeFontFamily(),
    fontSize = 48.sp,
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 56.sp,
    letterSpacing = (-0.04).em,
)

/** design.md `label-md`: 14sp / weight 500 / +0.05em. */
@Composable
fun labelMdStyle(): TextStyle = TextStyle(
    fontFamily = interFontFamily(),
    fontSize = 14.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 20.sp,
    letterSpacing = 0.05.em,
)

/** design.md `label-sm`: 12sp / weight 500 / +0.05em. */
@Composable
fun labelSmStyle(): TextStyle = TextStyle(
    fontFamily = interFontFamily(),
    fontSize = 12.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 16.sp,
    letterSpacing = 0.05.em,
)

@Composable
fun appTypography(): Typography {
    val manrope = manropeFontFamily()
    val inter = interFontFamily()
    return Typography(
        // headline-lg
        headlineLarge = TextStyle(
            fontFamily = manrope,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp,
            letterSpacing = (-0.02).em,
        ),
        // headline-sm
        headlineSmall = TextStyle(
            fontFamily = manrope,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp,
        ),
        // Акцентная цифра температуры в карточках — жирнее body/headline для мгновенного считывания.
        titleLarge = TextStyle(
            fontFamily = manrope,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 34.sp,
            letterSpacing = (-0.02).em,
        ),
        titleMedium = TextStyle(
            fontFamily = manrope,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.sp,
        ),
        // body-lg
        bodyLarge = TextStyle(
            fontFamily = inter,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 28.sp,
        ),
        // body-md
        bodyMedium = TextStyle(
            fontFamily = inter,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
        ),
        // label-md
        labelLarge = TextStyle(
            fontFamily = inter,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 20.sp,
        ),
        // label-sm
        labelMedium = TextStyle(
            fontFamily = inter,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 16.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = inter,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 14.sp,
        ),
    )
}
