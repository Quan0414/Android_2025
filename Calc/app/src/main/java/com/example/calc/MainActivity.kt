package com.example.calc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var result: TextView
    private var firstNumber = 0.0
    private var operation = ""
    private var isNewOperation = true
    private var curOpButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        result = findViewById(R.id.display)

        // Set up number buttons
        setupNumberButton(R.id.but0, "0")
        setupNumberButton(R.id.but1, "1")
        setupNumberButton(R.id.but2, "2")
        setupNumberButton(R.id.but3, "3")
        setupNumberButton(R.id.but4, "4")
        setupNumberButton(R.id.but5, "5")
        setupNumberButton(R.id.but6, "6")
        setupNumberButton(R.id.but7, "7")
        setupNumberButton(R.id.but8, "8")
        setupNumberButton(R.id.but9, "9")
        setupNumberButton(R.id.butDecimal, ",")

        // Set up operation buttons
        setupOperationButton(R.id.butAdd, "+")
        setupOperationButton(R.id.butSub, "-")
        setupOperationButton(R.id.butMulti, "*")
        setupOperationButton(R.id.butDivide, "/")


        // Thêm xử lý cho nút AC (All Clear)
        findViewById<Button>(R.id.butAC).setOnClickListener {
            result.text = "0"
            firstNumber = 0.0
            operation = ""
            isNewOperation = true
        }

        // Thêm xử lý cho nút Bằng
        findViewById<Button>(R.id.butEqual).setOnClickListener {
            calculateResult()
        }
    }



    private fun setupNumberButton(butId: Int, number: String) {
        findViewById<Button>(butId).setOnClickListener {
            if (isNewOperation) {
                result.text = number
                isNewOperation = false
            } else {
                // Xử lý dấu thập phân
                if (number == "," && result.text.contains(",")) {
                    return@setOnClickListener
                }
                result.text = result.text.toString() + number
            }
        }
    }

    private fun setupOperationButton(butId: Int, op: String) {
        findViewById<Button>(butId).setOnClickListener {
            // If there's already an operation in progress, calculate the result first
            if (!isNewOperation && operation.isNotEmpty()) {
                calculateResult()
            }

            // Store the current number and set up for the next operation
            firstNumber = result.text.toString().replace(",", ".").toDouble()
            operation = op
            isNewOperation = true
        }
    }

    private fun calculateResult() {
        if (operation.isNotEmpty()) {
            val secondNumber = result.text.toString().replace(",", ".").toDouble()
            var resultValue = 0.0

            when (operation) {
                "+" -> resultValue = firstNumber + secondNumber
                "-" -> resultValue = firstNumber - secondNumber
                "*" -> resultValue = firstNumber * secondNumber
                "/" -> {
                    if (secondNumber != 0.0) {
                        resultValue = firstNumber / secondNumber
                    } else {
                        result.text = "Lỗi"
                        return
                    }
                }
            }

            // Định dạng kết quả để tránh hiển thị số thập phân không cần thiết
            val resultString = if (resultValue == resultValue.toInt().toDouble()) {
                resultValue.toInt().toString()
            } else {

                val formatted = String.format("%.6f", resultValue)
                formatted.replace(Regex(",0+$"), "").replace(Regex("(\\d+\\.\\d*[1-9])0+$"), "$1")

            }
            result.text = resultString

            isNewOperation = true
        }
    }
}