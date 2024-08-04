package com.sujeong.composenote.feature_note.presentation.add_edit_note

import com.sujeong.composenote.feature_note.presentation.util.UiText

sealed class AddEditNoteUiEvent {
    data class ShowSnackBar(
        val message: UiText
    ): AddEditNoteUiEvent()

    data object SaveNote: AddEditNoteUiEvent()

}