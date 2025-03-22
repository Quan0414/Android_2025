package com.example.buoi2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Get the button from the layout
        val button = findViewById<Button>(R.id.buttonPanel)
        val num1 = findViewById<EditText>(R.id.number1EditText)
        val num2 = findViewById<EditText>(R.id.number2EditText)

        val result = findViewById<TextView>(R.id.resultText)

        // Set a click listener on the button
        button.setOnClickListener {
            // Get the text from the EditText
            val a = num1.text.toString().toInt()
            val b = num2.text.toString().toInt()
            val sum = a + b
            // Set the text of the TextView
            result.text = "Result: $sum"
        }

    }
}