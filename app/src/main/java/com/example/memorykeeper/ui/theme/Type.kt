package com.example.memorykeeper.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.memorykeeper.R

val displayFontFamily = FontFamily(
    Font(R.font.cossettetitre_regular, FontWeight.Normal),
    Font(R.font.cossettetitre_bold, FontWeight.Bold)
)

val baseline = Typography()

val AppTypography = Typography(

    // DISPLAY
    displayLarge = baseline.displayLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 42.sp
    ),
    displayMedium = baseline.displayMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    ),
    displaySmall = baseline.displaySmall.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp
    ),

    // HEADLINE
    headlineLarge = baseline.headlineLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = baseline.headlineMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    headlineSmall = baseline.headlineSmall.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),

    // TITLES
    titleLarge = baseline.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = baseline.titleMedium.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleSmall = baseline.titleSmall.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),

    // BODY
    bodyLarge = baseline.bodyLarge.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = baseline.bodyMedium.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = baseline.bodySmall.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),

    // LABELS
    labelLarge = baseline.labelLarge.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = baseline.labelMedium.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = baseline.labelSmall.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp
    )
)