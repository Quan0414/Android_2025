package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class NoteAdapter(
    private val onFavoriteChanged: (Item_Note) -> Unit
) : Adapter<NoteAdapter.NoteViewHolder>() {


    private val notes: MutableList<Item_Note> = mutableListOf()

    fun addNote(note: Item_Note) {
        notes.add(0, note)  // thêm vào đầu danh sách
        notifyItemInserted(0)
    }

    fun submitData(data: List<Item_Note>) {
        notes.clear()
        notes.addAll(data)
        notifyDataSetChanged()
    }

    fun getAllNotes(): List<Item_Note> {
        return notes.toList()
    }


    fun getFavoriteNotes(): List<Item_Note> {
        return notes.filter { it.favorite }
    }


    inner class NoteViewHolder(
        private val view: View
    ) : ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val content: TextView = view.findViewById(R.id.tvPreview)
        private val favorite: ImageView = view.findViewById(R.id.imgFav)

        fun bind(note: Item_Note) {
            title.text = note.title
            content.text = note.content

            // Set icon favorite ban đầu
            favorite.setImageResource(
                if (note.favorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )

            favorite.setOnClickListener {
                note.favorite = !note.favorite
                favorite.setImageResource(
                    if (note.favorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )
            }
            onFavoriteChanged(note)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }
}