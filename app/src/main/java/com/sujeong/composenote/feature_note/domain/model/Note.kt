package com.sujeong.composenote.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujeong.composenote.ui.theme.LightBlue
import com.sujeong.composenote.ui.theme.LightGreen
import com.sujeong.composenote.ui.theme.LightOrange
import com.sujeong.composenote.ui.theme.LightPink
import com.sujeong.composenote.ui.theme.LightYellow

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(
            LightBlue,
            LightYellow,
            LightGreen,
            LightOrange,
            LightPink
        )
    }
}

sealed class InvalidNoteException: Exception() {
    data object EmptyTitle: InvalidNoteException()
    data object EmptyContent: InvalidNoteException()
}
