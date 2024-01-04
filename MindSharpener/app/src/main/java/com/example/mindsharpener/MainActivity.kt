package com.example.mindsharpener

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.Random

class MainActivity : AppCompatActivity() {

    // Initialize variables for operands and operator
    private var operand1: Int = 0
    private var operand2: Int = 0
    private var operator: Int = 0

    // Initialize points
    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get references to views
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val editTextAnswer = findViewById<EditText>(R.id.editTextAnswer)
        val buttonCheck = findViewById<Button>(R.id.buttonCheck)
        val levelRadioGroup = findViewById<RadioGroup>(R.id.levelRadioGroup)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val textView5 = findViewById<TextView>(R.id.textView5)
        val textView6 = findViewById<TextView>(R.id.textView6)
        val pointTextView = findViewById<TextView>(R.id.pointTextView)

        // Set up click listener for the button
        buttonCheck.setOnClickListener {
            // Handle button click
            checkAnswer(editTextAnswer.text.toString())
        }

        // Set up click listener for the level radio group
        levelRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Handle level change
            setOperandsAndOperator(checkedId)
            displayQuestion()
        }

        // Initial setup
        setOperandsAndOperator(levelRadioGroup.checkedRadioButtonId)
        displayQuestion()
    }

    private fun setOperandsAndOperator(checkedId: Int) {
        // Instantiate random class
        val random = Random()

        // Determine which radiobutton is checked
        when (checkedId) {
            R.id.radioButtonI3 -> {
                // Generate 1 random number between 0 - 9
                operand1 = random.nextInt(10)
                operand2 = random.nextInt(10)
            }
            R.id.radioButtonI5 -> {
                // Generate 2 random numbers between 0 - 99
                operand1 = random.nextInt(100)
                operand2 = random.nextInt(100)
            }
            R.id.radioButtonI7 -> {
                // Generate 2 random numbers between 0 - 999
                operand1 = random.nextInt(1000)
                operand2 = random.nextInt(1000)
            }
        }

        // Generate a random number for operator (0 to 3)
        operator = random.nextInt(4)
    }

    private fun displayQuestion() {
        findViewById<TextView>(R.id.textView4).text = operand1.toString()
        findViewById<TextView>(R.id.textView5).text = getOperatorSymbol(operator)
        findViewById<TextView>(R.id.textView6).text = operand2.toString()
    }

    private fun checkAnswer(userAnswer: String) {
        val correctAnswer = when (operator) {
            0 -> operand1 + operand2
            1 -> operand1 - operand2
            2 -> operand1 * operand2
            3 -> if (operand2 != 0) operand1 / operand2 else 0
            else -> 0
        }

        // Compare the answer with the user's answer
        if (userAnswer.isNotEmpty() && userAnswer.toInt() == correctAnswer) {
            points++
        } else {
            points--
        }

        // Display points with specified colors
        val pointColor = Color.parseColor("#FF0000") // Red
        val headerTextColor = Color.parseColor("#FFFFFF") // White
        val darkBlueColor = Color.parseColor("#00008B") // Dark Blue

        findViewById<TextView>(R.id.pointTextView).apply {
            text = "POINT: $points"
            setTextColor(darkBlueColor)
        }

        // Display another question
        setOperandsAndOperator(findViewById<RadioGroup>(R.id.levelRadioGroup).checkedRadioButtonId)
        displayQuestion()

        // Clear the answer field
        findViewById<EditText>(R.id.editTextAnswer).text.clear()
    }

    private fun getOperatorSymbol(operator: Int): String {
        return when (operator) {
            0 -> "+"
            1 -> "-"
            2 -> "*"
            3 -> "/"
            else -> ""
        }
    }
}
