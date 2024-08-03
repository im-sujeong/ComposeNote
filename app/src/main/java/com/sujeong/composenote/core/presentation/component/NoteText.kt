package com.sujeong.composenote.core.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sujeong.composenote.R

val noteFontFamily = FontFamily(
    Font(R.font.suit_light, FontWeight.Light),
    Font(R.font.suit_medium, FontWeight.Medium),
    Font(R.font.suit_semi_bold, FontWeight.SemiBold),
    Font(R.font.suit_extra_bold, FontWeight.ExtraBold)
)

@Composable
fun NoteText(
    text: String,
    modifier: Modifier = Modifier,
    noteTextStyle: NoteTextStyle = NoteTextStyle.BODY_SMALL,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start,
    maxLine: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        fontFamily = noteFontFamily,
        fontWeight = noteTextStyle.fontWeight,
        fontSize = noteTextStyle.fontSize,
        textAlign = textAlign,
        color = color,
        maxLines = maxLine,
        overflow = overflow
    )
}

enum class NoteTextStyle(
    val fontWeight: FontWeight,
    val fontSize: TextUnit
) {
    HEADLINE(FontWeight.ExtraBold, 40.sp),
    TITLE_SMALL(FontWeight.SemiBold, 16.sp),
    BODY_LARGE(FontWeight.Medium, 18.sp),
    BODY_SMALL(FontWeight.Medium, 14.sp),
    LABEL(FontWeight.Light, 12.sp),
    LABEL_BOLD(FontWeight.SemiBold, 12.sp),
}

@Preview(showBackground = true)
@Composable
fun NoteTextPreview() {
    Column {
        NoteText(
            "안녕하세요 헤.드.라.인",
            noteTextStyle = NoteTextStyle.HEADLINE
        )

        NoteText(
            "안녕하세요 TITLE SMALL",
            noteTextStyle = NoteTextStyle.TITLE_SMALL
        )

        NoteText(
            "안녕하세요 BODY LARGE",
            noteTextStyle = NoteTextStyle.BODY_LARGE
        )

        NoteText(
            "안녕하세요 BODY SMALL",
            noteTextStyle = NoteTextStyle.BODY_SMALL
        )

        NoteText(
            "안녕하세요 LABEL",
            noteTextStyle = NoteTextStyle.LABEL
        )

        NoteText(
            "안녕하세요 LABEL",
            noteTextStyle = NoteTextStyle.LABEL_BOLD
        )
    }
}
