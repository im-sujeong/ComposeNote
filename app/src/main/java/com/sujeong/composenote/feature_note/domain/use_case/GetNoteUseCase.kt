package com.sujeong.composenote.feature_note.domain.use_case

import com.sujeong.composenote.feature_note.domain.model.Note
import com.sujeong.composenote.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return noteRepository.getNoteById(id)
    }
}