package com.sujeong.composenote.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujeong.composenote.feature_note.domain.model.Note
import com.sujeong.composenote.feature_note.domain.use_case.NoteUseCases
import com.sujeong.composenote.feature_note.domain.util.NoteOrder
import com.sujeong.composenote.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {
    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeleteNote: Note? = null

    private var getNoteJob: Job? = null

    init {
        getNotes(noteOrder = NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {
                if(state.value.noteOrder::class.java == event.noteOrder::class.java
                    && state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }

                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> deleteNote(note = event.note)
            NotesEvent.RestoreNote -> restoreNote(note = recentlyDeleteNote)
            NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNoteJob?.cancel()
        getNoteJob = noteUseCases.getNotesUseCase(noteOrder)
            .onEach {
                _state.value = state.value.copy(
                    notes = it,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }

    private fun deleteNote(note: Note) = viewModelScope.launch {
        noteUseCases.deleteNoteUseCase(note)
        recentlyDeleteNote = note
    }

    private fun restoreNote(note: Note?) = viewModelScope.launch(
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }
    ){
        noteUseCases.addNoteUseCase(note ?: return@launch)
        recentlyDeleteNote = null
    }
}