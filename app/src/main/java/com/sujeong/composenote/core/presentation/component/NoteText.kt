package com.sujeong.composenote.core.presentation.component

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sujeong.composenote.R

val noteFontFamily = FontFamily(
    Font(R.font.suit_light, FontWeight.Light),
    Font(R.font.suit_medium, FontWeight.Medium),
    Font(R.font.suit_semi_bold, FontWeight.SemiBold),
    Font(R.font.suit_extra_bold, FontWeight.ExtraBold)
)

enum class NoteTextStyle(
    private val fontWeight: FontWeight,
    private val fontSize: TextUnit
) {
    HEADLINE(FontWeight.ExtraBold, 24.sp),
    TITLE_SMALL(FontWeight.SemiBold, 16.sp),
    BODY_LARGE(FontWeight.Medium, 18.sp),
    BODY_SMALL(FontWeight.Medium, 14.sp),
    LABEL(FontWeight.Medium, 12.sp),
    LABEL_BOLD(FontWeight.SemiBold, 12.sp);

    fun toTextStyle(): TextStyle {
        return TextStyle(
            fontFamily = noteFontFamily,
            fontWeight = this.fontWeight,
            fontSize = this.fontSize
        )
    }
}
