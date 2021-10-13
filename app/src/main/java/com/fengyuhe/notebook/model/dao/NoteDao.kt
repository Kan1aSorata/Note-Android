package com.fengyuhe.notebook.model.dao

import androidx.room.*
import com.fengyuhe.notebook.model.Note

@Dao
interface NoteDao {
    @Insert
    fun insertNote(node: Note?): Long

    @Update
    fun updateNote(note: Note)

    @Query("select * from Note")
    fun loadAllNotes(): List<Note>

    @Delete
    fun deleteNote(note: Note)

    @Query("select * from Note where id = :id")
    fun queryNoteById(id: Long): Note?

    @Query(
        "select * from Note where " +
    "title like '%' || :condition || '%' or " +
    "content like '%' || :condition || '%'")
    fun queryNoteByCondition(condition: String): List<Note>
}

