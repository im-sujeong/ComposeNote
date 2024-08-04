package com.sujeong.composenote.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.sujeong.composenote.core.presentation.component.NoteTextStyle
import com.sujeong.composenote.feature_note.domain.model.Note
import com.sujeong.composenote.ui.theme.DarkGray
import com.sujeong.composenote.ui.theme.Gray
import com.sujeong.composenote.ui.theme.LightPink
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )

                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(note.color, 0x0000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), - 100f),
                    size = Size(
                        cutCornerSize.toPx() + 100f,
                        cutCornerSize.toPx() + 100f
                    ),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                color = DarkGray,
                modifier = Modifier.padding(end = 32.dp),
                style = NoteTextStyle.TITLE_SMALL.toTextStyle()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.content,
                color = DarkGray,
                style = NoteTextStyle.BODY_SMALL.toTextStyle(),
                textAlign = TextAlign.Justify,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            val date = SimpleDateFormat(
                "yyyy.MM.dd HH:mm", Locale.KOREA
            ).format(Date(note.timestamp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = date,
                    style = NoteTextStyle.LABEL.toTextStyle(),
                    textAlign = TextAlign.Justify,
                    color = Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.BottomEnd),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "삭제",
                        tint = DarkGray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NoteItemPreview() {
    NoteItem(
        note = Note(
            title = "노트 제목이 들어갑니다.",
            content = "노트의 내용이 이곳에 들어갑니다. ",
            timestamp = 1720185343635,
            color = LightPink.toArgb()
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) { }
}