package com.sujeong.composenote.feature_note.domain.use_case

import android.util.Log
import com.sujeong.composenote.feature_note.domain.model.InvalidNoteException
import com.sujeong.composenote.feature_note.domain.model.Note
import com.sujeong.composenote.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        Log.v("sujeongTAG", "000")

        if(note.title.isBlank()) {
            throw InvalidNoteException.EmptyTitle
        }

        Log.v("sujeongTAG", "111")

        if(note.content.isBlank()) {
            throw InvalidNoteException.EmptyContent
        }

        Log.v("sujeongTAG", "222")

        noteRepository.insertNote(note)
    }
}