package com.example.notes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter

    private val allNotes = mutableListOf<Item_Note>()   // List lưu toàn bộ ghi chú
    private var showingFavorites = false                // Cờ để biết đang xem All hay Favorites

    // Thêm ở ngoài onCreate
    private val addNoteLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val title = data?.getStringExtra("note_title")
            val content = data?.getStringExtra("note_content")

            if (!title.isNullOrBlank() && !content.isNullOrBlank()) {
                val newNote = Item_Note(title, content, false)
                allNotes.add(0, newNote)
                updateDisplayedList()

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAdapter = NoteAdapter { updatedNote ->
            val index = allNotes.indexOfFirst {
                it.title == updatedNote.title && it.content == updatedNote.content
            }
            if (index != -1) {
                allNotes[index] = updatedNote
            }
        }


        // Chuyen man Add note
        binding.tvAddNote.setOnClickListener {
            val intent = Intent(this, NewNote::class.java)
            addNoteLauncher.launch(intent)
        }

        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // Bấm Tab "All Notes"
        binding.tabAll.setOnClickListener {
            showingFavorites = false
            updateDisplayedList()
        }

        // Bấm Tab "Favorites"
        binding.tabFav.setOnClickListener {
            showingFavorites = true
            updateDisplayedList()
        }

    }

    // Hàm cập nhật dữ liệu hiển thị
    private fun updateDisplayedList() {
        val dataToShow = if (showingFavorites) {
            allNotes.filter { it.favorite }
        } else {
            allNotes
        }
        noteAdapter.submitData(dataToShow)
    }


}