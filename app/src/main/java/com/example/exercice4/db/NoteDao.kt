package com.example.exercice4.db

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: Note)

    @Query("Select * From Note Order BY id DESC")
    suspend fun getAllNotes(): List<Note>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)


}

