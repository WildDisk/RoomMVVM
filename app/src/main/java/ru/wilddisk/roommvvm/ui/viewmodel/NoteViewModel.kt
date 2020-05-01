package ru.wilddisk.roommvvm.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ru.wilddisk.roommvvm.data.model.Note
import ru.wilddisk.roommvvm.data.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private var repository = NoteRepository(application)
    private lateinit var allNotes: LiveData<List<Note>>
    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes() = repository.getAllNotes()
    fun getMaxId() = repository.getMaxId()

    fun hardInsert() = repository.hardInsert()
}