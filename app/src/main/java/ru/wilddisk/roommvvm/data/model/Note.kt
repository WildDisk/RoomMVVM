package ru.wilddisk.roommvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    var title: String?,
    var descriptor: String?,
    var priority: Int?
) {
    @PrimaryKey(autoGenerate = true)
    private var id: Int? = 0

    fun setId(id: Int?) {
        this.id = id
    }

    fun getId(): Int? = id
}