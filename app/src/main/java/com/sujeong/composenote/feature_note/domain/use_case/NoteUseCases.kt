package com.sujeong.composenote.feature_note.domain.use_case

import javax.inject.Inject

data class NoteUseCases @Inject constructor(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase
)