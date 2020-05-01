package ru.wilddisk.roommvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_note.*
import ru.wilddisk.roommvvm.R

class AddEditNoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add note"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title = edit_text_title.text.toString()
        val description = edit_text_description.text.toString()
        val priority = number_picker_priority.value
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent()
        intent.putExtra(EXTRA_TITLE, title)
        intent.putExtra(EXTRA_DESCRIPTION, description)
        intent.putExtra(EXTRA_PRIORITY, priority)
        val id = getIntent().getIntExtra(EXTRA_ID, -1)
        if (id != -1) intent.putExtra(EXTRA_ID, id)
        setResult(RESULT_OK, intent)
        finish()
    }
}