package ru.wilddisk.roommvvm.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ru.wilddisk.roommvvm.R
import ru.wilddisk.roommvvm.data.model.Note
import ru.wilddisk.roommvvm.ui.adapter.NoteAdapter
import ru.wilddisk.roommvvm.ui.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity(), NoteAdapter.ItemClickListener {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_add_note.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
        noteViewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]
        noteViewModel.getAllNotes()?.observe(this, Observer {
            this.renderData(it)
        })
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    noteViewModel.delete(
                        adapter.getNoteAt(viewHolder.adapterPosition)
                    )
                    Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show();
                }
            }
        ).attachToRecyclerView(recycler_view)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK -> {
                val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
                val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
                val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)
                val note = Note(title, description, priority)
                noteViewModel.insert(note)
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            }
            requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK -> {
                val id = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
                if (id == -1) {
                    Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                    return
                }
                val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
                val description = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
                val priority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)
                val note = Note(title, description, priority)
                note.setId(id)
                noteViewModel.update(note)
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(this, "Note is not updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun renderData(notes: List<Note>) {
        adapter = NoteAdapter(this, notes, this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recycler_view.layoutManager = layoutManager
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(note: Note) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId())
        intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
        intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.descriptor)
        intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
        startActivityForResult(intent, EDIT_NOTE_REQUEST)
    }
}
