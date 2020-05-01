package ru.wilddisk.roommvvm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.wilddisk.roommvvm.data.model.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT MAX(id) FROM note_table")
    fun getMaxId(): LiveData<Int>

    @Query("INSERT INTO note_table('title', 'descriptor', 'priority') VALUES ('title1', 'descriptor1', 'priority1')")
    fun hardInsert()
}