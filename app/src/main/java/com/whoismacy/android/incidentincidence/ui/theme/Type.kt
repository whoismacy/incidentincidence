package com.whoismacy.android.incidentincidence.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.whoismacy.android.incidentincidence.R

val MontSerratFont =
    FontFamily(
        Font(R.font.montserrat_extralight, FontWeight.ExtraLight),
        Font(R.font.montserrat_thin, FontWeight.Thin),
        Font(R.font.montserrat_black, FontWeight.Black),
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold),
        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
    )

// Set of Material typography styles to start with
val Typography =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Bold,
                fontSize = 57.sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
            ),
        headlineLarge =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = MontSerratFont,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
    )
