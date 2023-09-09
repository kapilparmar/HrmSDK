package com.connectandheal.hrmsdk.view.theme.hrm.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.R

val HelveticaFontFamily = FontFamily(
    listOf(
        Font(R.font.helvetica, FontWeight.Normal),
        Font(R.font.helvetica_light, FontWeight.Light),
        Font(R.font.helvetica_bold, FontWeight.Bold)
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFontFamily = FontFamily(
    listOf(
        Font(DeviceFontFamilyName("sans-serif"), FontWeight.Normal),
        Font(DeviceFontFamilyName("sans-serif-light"), FontWeight.Light),
        Font(DeviceFontFamilyName("sans-serif-medium"), FontWeight.Medium),
    )
)
val IndieFontFamily = FontFamily(
    listOf(
        Font(R.font.indie_flower_regular, FontWeight.Normal),
    )
)
val MallannaFontFamily = FontFamily(
    listOf(
        Font(R.font.mallanna_regular, FontWeight.Normal),
    )
)

val defaultFontFamily = HelveticaFontFamily

val Typography = androidx.compose.material3.Typography(

    displayLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = HelveticaFontFamily,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = HelveticaFontFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = HelveticaFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = HelveticaFontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = HelveticaFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 10.sp,
        lineHeight = 16.sp
    )

)

val androidx.compose.material3.Typography.topAppBarTitleBold: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 1.sp
    )

val androidx.compose.material3.Typography.topAppBarTitleNormal: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 1.sp
    )
val androidx.compose.material3.Typography.topAppBarTitleNormalMedium: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp
    )
val androidx.compose.material3.Typography.badgeLabelBold: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 1.sp
    )

val androidx.compose.material3.Typography.badgeLabelNormal: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 1.sp
    )

val androidx.compose.material3.Typography.badgeLabelSemi: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 8.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 1.sp
    )

val androidx.compose.material3.Typography.mainHeading: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 32.sp
    )
val androidx.compose.material3.Typography.pageHeading: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 30.sp
    )
val androidx.compose.material3.Typography.pageTitle: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 30.sp
    )

val androidx.compose.material3.Typography.subHeadingLarge: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 24.sp
    )
val androidx.compose.material3.Typography.subHeadingMedium: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 24.sp
    )

val androidx.compose.material3.Typography.bodyBoldLarge: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 24.sp
    )
val androidx.compose.material3.Typography.bodyBoldMedium: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 20.sp
    )
val androidx.compose.material3.Typography.bodyBoldSmall: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    )
val androidx.compose.material3.Typography.bodyBoldExtraSmall: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    )
val androidx.compose.material3.Typography.bodyRegularLarge: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 24.sp
    )
val androidx.compose.material3.Typography.bodyRegularMedium: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 20.sp
    )

val androidx.compose.material3.Typography.bodyRegularSmall: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    )
val androidx.compose.material3.Typography.bodyRegularExtraSmall: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    )

val androidx.compose.material3.Typography.paragraphRegularLarge: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 24.sp
    )

val androidx.compose.material3.Typography.paragraphRegularMedium: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 20.sp
    )

val androidx.compose.material3.Typography.paragraphRegularSmall: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    )

val androidx.compose.material3.Typography.strikeBodyRegularSmall: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = HelveticaFontFamily,
        letterSpacing = 0.sp,
        lineHeight = 16.sp,
        textDecoration = TextDecoration.LineThrough
    )

val TextStyle_Size12_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 12.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size14_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size16_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size18_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size18_Weight500: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size20_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        fontFamily = defaultFontFamily
    )
val TextStyle_Size20_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 20.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size10_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 10.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size12_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size14_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size13_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 13.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size15_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 15.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size13_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 13.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size16_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size18_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size24_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size24_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size28_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        fontFamily = defaultFontFamily
    )

val TextStyle_Size32_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        fontFamily = defaultFontFamily
    )
val TextStyle_Size32_Weight400: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        fontFamily = defaultFontFamily
    )
val TextStyle_Size22_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 22.sp,
        fontFamily = defaultFontFamily
    )
val TextStyle_Size19_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 19.sp,
        fontFamily = defaultFontFamily
    )
val TextStyle_Size34_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 34.sp,
        fontFamily = defaultFontFamily
    )
val TextStyle_Size36_Weight700: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 36.sp,
        fontFamily = defaultFontFamily
    )