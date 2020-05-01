package ru.wilddisk.roommvvm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*
import ru.wilddisk.roommvvm.R
import ru.wilddisk.roommvvm.data.model.Note

class NoteAdapter(
    private val context: Context,
    private var items: List<Note>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun getNoteAt(position: Int): Note = items[position]

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Note) = with(itemView) {
            text_view_title?.text = item.title
            text_view_description?.text = item.descriptor
            text_view_priority?.text = item.priority.toString()
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    clickListener.onItemClick(items[adapterPosition])
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(note: Note)
    }
}