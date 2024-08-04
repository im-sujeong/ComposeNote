package com.sujeong.composenote.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujeong.composenote.R
import com.sujeong.composenote.feature_note.domain.model.InvalidNoteException
import com.sujeong.composenote.feature_note.domain.model.Note
import com.sujeong.composenote.feature_note.domain.use_case.NoteUseCases
import com.sujeong.composenote.feature_note.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteUseCases: NoteUseCases
): ViewModel() {
    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = UiText.StringResource(R.string.add_edit_note_title_hint)
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = UiText.StringResource(R.string.add_edit_note_content_hint)
        )
    )

    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<AddEditNoteUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let {
            getNote(noteId = it)
        }
    }

    private fun getNote(noteId: Int) {
        if(noteId > 0) {
            viewModelScope.launch {
                noteUseCases.getNoteUseCase(noteId)?.also { note ->
                    currentNoteId = note.id

                    _noteTitle.value = noteTitle.value.copy(
                        text = note.title,
                        isHintVisible = false
                    )

                    _noteContent.value = noteContent.value.copy(
                        text = note.content,
                        isHintVisible = false
                    )

                    _noteColor.intValue = note.color
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.intValue = event.color
            }

            AddEditNoteEvent.SaveNote -> saveNote()
        }
    }

    private fun saveNote() = viewModelScope.launch {
        try {
            noteUseCases.addNoteUseCase(
                Note(
                    title = noteTitle.value.text,
                    content = noteContent.value.text,
                    timestamp = System.currentTimeMillis(),
                    color = noteColor.value,
                    id = currentNoteId
                )
            )

            _eventFlow.emit(AddEditNoteUiEvent.SaveNote)
        }catch (e: InvalidNoteException) {
            when(e) {
                is InvalidNoteException.EmptyTitle -> {
                    _eventFlow.emit(
                        AddEditNoteUiEvent.ShowSnackBar(
                            UiText.StringResource(
                                resId = R.string.add_edit_note_title_error
                            )
                        )
                    )
                }

                is InvalidNoteException.EmptyContent -> {
                    _eventFlow.emit(
                        AddEditNoteUiEvent.ShowSnackBar(
                            UiText.StringResource(
                                resId = R.string.add_edit_note_content_error
                            )
                        )
                    )
                }
            }
        }
    }
}