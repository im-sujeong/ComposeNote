package com.sujeong.composenote.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sujeong.composenote.core.presentation.component.NoteTextStyle
import com.sujeong.composenote.ui.theme.Gray

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    noteTextStyle: NoteTextStyle = NoteTextStyle.BODY_LARGE,
    textColor: Color = Color.Black,
    hintColor: Color = Gray,
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = noteTextStyle.toTextStyle()
                .copy(
                    color = textColor
                ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
        )

        if(isHintVisible) {
            Text(
                text = hint,
                style = noteTextStyle.toTextStyle(),
                color = hintColor
            )
        }
    }
}

@Preview
@Composable
fun TransparentHintTextFieldPreview() {
    TransparentHintTextField(
        text = "",
        hint = "입력해주세요",
        onValueChange = {} ,
        onFocusChange = {}
    )
}