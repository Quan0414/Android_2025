package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityAddNoteBinding

class NewNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSaveNote.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()

            if (title.isNotBlank() && content.isNotBlank()) {
                val intent = Intent()
                intent.putExtra("note_title", title)
                intent.putExtra("note_content", content)
                setResult(RESULT_OK, intent)
                finish()  // Quay lại MainActivity
            } else {
                // Nếu thiếu dữ liệu thì không làm gì hoặc hiện Toast (tùy bạn)
                Toast(this).apply {
                    setText("Vui lòng nhập tiêu đề và nội dung")
                    show()
                }
            }
        }

    }
}