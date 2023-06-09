package com.alissakayz.question2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)
        val editTextProtein = findViewById<EditText>(R.id.editTextProtein)
        val editTextFat = findViewById<EditText>(R.id.editTextFat)
        val editTextCarbohydrate = findViewById<EditText>(R.id.editTextCarbohydrate)
        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val textViewCaloriesProtein = findViewById<TextView>(R.id.textViewCaloriesProtein)
        val textViewCaloriesFat = findViewById<TextView>(R.id.textViewCaloriesFat)
        val textViewCaloriesCarbohydrate = findViewById<TextView>(R.id.textViewCaloriesCarbohydrate)
        val textViewTotalCalories = findViewById<TextView>(R.id.textViewTotalCalories)
        val textViewPercentageProtein = findViewById<TextView>(R.id.textViewPercentageProtein)
        val textViewPercentageFat = findViewById<TextView>(R.id.textViewPercentageFat)
        val textViewPercentageCarbohydrate = findViewById<TextView>(R.id.textViewPercentageCarbohydrate)
        val buttonNavigate = findViewById<Button>(R.id.buttonNavigate) // question 2
        val buttonAddFood = findViewById<Button>(R.id.buttonAddFood)  //question 4 - add new items to csv file

        buttonCalculate.setOnClickListener {
            val protein = editTextProtein.text.toString().toDouble()
            val fat = editTextFat.text.toString().toDouble()
            val carbohydrate = editTextCarbohydrate.text.toString().toDouble()
            val weight = editTextWeight.text.toString().toDouble()

            if (protein < 0 || fat < 0 || carbohydrate < 0 || weight <= 0) {
                textViewCaloriesProtein.text = "Invalid. Please try again."
                textViewCaloriesFat.text = ""
                textViewCaloriesCarbohydrate.text = ""
                textViewTotalCalories.text = ""
                textViewPercentageProtein.text = ""
                textViewPercentageFat.text = ""
                textViewPercentageCarbohydrate.text = ""
                buttonSave.visibility = View.GONE
            } else {
                val caloriesProtein = (protein * 4) * (weight / 100)
                val caloriesFat = (fat  * 9) * (weight / 100)
                val caloriesCarbohydrate = (carbohydrate  * 4) * (weight / 100)
                val totalCalories = caloriesProtein + caloriesFat + caloriesCarbohydrate

                textViewCaloriesProtein.text = "Protein: ${"%.2f".format(caloriesProtein)} calories"
                textViewCaloriesFat.text = "Fat: ${"%.2f".format(caloriesFat)} calories"
                textViewCaloriesCarbohydrate.text = "Carbohydrate: ${"%.2f".format(caloriesCarbohydrate)} calories"
                textViewTotalCalories.text = "Total Calories: ${"%.2f".format(totalCalories)} calories"

                val percentageProtein = (caloriesProtein / totalCalories) * 100
                val percentageFat = (caloriesFat / totalCalories) * 100
                val percentageCarbohydrate = (caloriesCarbohydrate / totalCalories) * 100

                textViewPercentageProtein.text = "Protein: ${"%.2f".format(percentageProtein)}%"
                textViewPercentageFat.text = "Fat: ${"%.2f".format(percentageFat)}%"
                textViewPercentageCarbohydrate.text = "Carbohydrate: ${"%.2f".format(percentageCarbohydrate)}%"

                buttonSave.visibility = View.VISIBLE // saving data functionality
            }
        }

        buttonSave.setOnClickListener {
            val protein = editTextProtein.text.toString()
            val fat = editTextFat.text.toString()
            val carbohydrate = editTextCarbohydrate.text.toString()
            val weight = editTextWeight.text.toString()

            val result = """
                |Protein: $protein g
                |Fat: $fat g
                |Carbohydrate: $carbohydrate g
                |Weight: $weight g
            """.trimMargin()

            saveData(result)
        }
        buttonNavigate.setOnClickListener {// Navigate to question 2
            val intent = Intent(this@MainActivity, Question2Activity::class.java)
            startActivity(intent)
        }
        buttonAddFood.setOnClickListener{ // Navigate to question 4
            val intent = Intent(this@MainActivity, Question4::class.java)
            startActivity(intent)
        }

    }

    private fun saveData(data: String) { //external storage
        val fileName = "results.txt"
        val file = File(getExternalFilesDir(null), fileName)

        try {
            FileOutputStream(file, true).use { fos ->
                OutputStreamWriter(fos).use { writer ->
                    writer.append(data)
                }
            }
            Toast.makeText(this, "Data saved to $fileName", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
