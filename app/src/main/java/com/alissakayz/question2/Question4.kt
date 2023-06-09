package com.alissakayz.question2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileWriter

class Question4: AppCompatActivity() {
    private val filePath = "SAC DB.csv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question4)

        val editTextFood = findViewById<EditText>(R.id.editTextFood)
        val editTextMeasure = findViewById<EditText>(R.id.editTextMeasure)
        val editTextGrams = findViewById<EditText>(R.id.editTextGrams)
        val editTextCalories = findViewById<EditText>(R.id.editTextCalories)
        val editTextProtein = findViewById<EditText>(R.id.editTextProtein)
        val editTextSatFat = findViewById<EditText>(R.id.editTextSatFat)
        val editTextFiber = findViewById<EditText>(R.id.editTextFiber)
        val editTextCarbs = findViewById<EditText>(R.id.editTextCarbs)
        val editTextCategory = findViewById<EditText>(R.id.editTextCategory)
        val buttonAddFood = findViewById<Button>(R.id.buttonAddFood)

        buttonAddFood.setOnClickListener {
            val food = editTextFood.text.toString()
            val measure = editTextMeasure.text.toString()
            val grams = editTextGrams.text.toString()
            val calories = editTextCalories.text.toString()
            val protein = editTextProtein.text.toString()
            val satFat = editTextSatFat.text.toString()
            val fiber = editTextFiber.text.toString()
            val carbs = editTextCarbs.text.toString()
            val category = editTextCategory.text.toString()

            // Create a new line of CSV data
            val newLine = "$food,$measure,$grams,$calories,$protein,$satFat,$fiber,$carbs,$category" //create new line in csv

            addFoodToCSV(newLine) //add line

            Toast.makeText(this, "Food added successfully", Toast.LENGTH_SHORT).show() //react after successfully received the data and save to csv

            clearInputFields()
        }
    }

    private fun addFoodToCSV(newLine: String) {
        try {
            val file = File(filesDir, filePath)
            val writer = FileWriter(file, true)
            writer.append(newLine)
            writer.append("\n")
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clearInputFields() {
        val editTextList = listOf<EditText>(
            findViewById(R.id.editTextFood),
            findViewById(R.id.editTextMeasure),
            findViewById(R.id.editTextGrams),
            findViewById(R.id.editTextCalories),
            findViewById(R.id.editTextProtein),
            findViewById(R.id.editTextSatFat),
            findViewById(R.id.editTextFiber),
            findViewById(R.id.editTextCarbs),
            findViewById(R.id.editTextCategory)
        )

        for (editText in editTextList) {
            editText.text.clear()
        }
    }
}
