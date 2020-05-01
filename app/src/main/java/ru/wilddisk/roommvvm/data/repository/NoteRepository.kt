package ru.wilddisk.roommvvm.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.wilddisk.roommvvm.data.dao.NoteDao
import ru.wilddisk.roommvvm.data.db.AppDatabase
import ru.wilddisk.roommvvm.data.model.Note
import kotlin.coroutines.CoroutineContext

class NoteRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private var noteDao: NoteDao?

    init {
        val db = AppDatabase.getDatabase(application)
        noteDao = db?.noteDao()
    }

    private lateinit var allNotes: LiveData<List<Note>>
    fun insert(note: Note) {
        launch { insertNoteAsync(note) }
    }

    private suspend fun insertNoteAsync(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao?.insert(note)
        }
    }

    fun update(note: Note) {
        launch { updateNoteAsync(note) }
    }

    private suspend fun updateNoteAsync(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao?.update(note)
        }
    }

    fun delete(note: Note) {
        launch { deleteNoteAsync(note) }
    }

    private suspend fun deleteNoteAsync(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao?.delete(note)
        }
    }

    fun deleteAllNotes() {
        launch { deleteAllNotesAsync() }
    }

    private suspend fun deleteAllNotesAsync() {
        withContext(Dispatchers.IO) {
            noteDao?.deleteAllNotes()
        }
    }

    fun getAllNotes() = noteDao?.getAllNotes()
    fun getMaxId() = noteDao?.getMaxId()?.value

    fun hardInsert() {
        launch { hardInsertAsync() }
    }

    private suspend fun hardInsertAsync() {
        withContext(Dispatchers.IO) {
            noteDao?.hardInsert()
        }
    }
}