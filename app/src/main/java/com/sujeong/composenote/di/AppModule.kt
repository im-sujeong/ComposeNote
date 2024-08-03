package com.sujeong.composenote.di

import android.app.Application
import androidx.room.Room
import com.sujeong.composenote.feature_note.data.data_source.NoteDatabase
import com.sujeong.composenote.feature_note.data.repository.NoteRepositoryImpl
import com.sujeong.composenote.feature_note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(
        app: Application
    ): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(
            dao = noteDatabase.noteDao
        )
    }
}