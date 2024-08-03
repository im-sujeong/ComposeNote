package com.sujeong.composenote.feature_note.domain.use_case

import com.sujeong.composenote.feature_note.domain.model.InvalidNoteException
import com.sujeong.composenote.feature_note.domain.model.Note
import com.sujeong.composenote.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("제목을 입력해주세요.")
        }

        if(note.content.isBlank()) {
            throw InvalidNoteException("내용을 입력해주세요.")
        }

        noteRepository.insertNote(note)
    }
}