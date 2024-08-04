package com.sujeong.composenote.feature_note.presentation.add_edit_note

import com.sujeong.composenote.feature_note.presentation.util.UiText

data class NoteTextFieldState(
    val text: String = "",
    val hint: UiText = UiText.DynamicString(""),
    val isHintVisible: Boolean = true
)