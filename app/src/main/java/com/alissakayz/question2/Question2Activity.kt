package com.alissakayz.question2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class Question2Activity : AppCompatActivity() {
    private lateinit var searchText: EditText
    private lateinit var searchButton: Button
    private lateinit var resultTextView: TextView

    private val foodList: MutableList<Food> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question2)

        searchText = findViewById(R.id.editTextSearch)
        searchButton = findViewById(R.id.buttonSearch)
        resultTextView = findViewById(R.id.textViewResult)

        readCSVFile()

        searchButton.setOnClickListener {
            val searchValue = searchText.text.toString().trim()
            val searchResults = searchFood(searchValue)

            if (searchResults.isNotEmpty()) {
                val resultText = buildSearchResultText(searchResults)
                resultTextView.text = resultText
            } else {
                resultTextView.text = "No results found for \"$searchValue\""
            }
        }
    }

    private fun readCSVFile() {
        val inputStream = assets.open("SAC DB.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?

        reader.readLine() // Skip the header line

        while (reader.readLine().also { line = it } != null) {
            line?.split(",")?.let { rowData ->
                if (rowData.size >= 9) {
                    val food = Food(
                        rowData[0].trim(),
                        rowData[1].trim(),
                        rowData[2].trim(),
                        rowData[3].trim(),
                        rowData[4].trim(),
                        rowData[5].trim(),
                        rowData[6].trim(),
                        rowData[7].trim(),
                        rowData[8].trim()
                    )
                    foodList.add(food)
                }
            }
        }

        reader.close()
    }

    private fun searchFood(searchValue: String): List<Food> {
        return foodList.filter { food ->
            food.food.contains(searchValue, ignoreCase = true) ||
                    food.measure.contains(searchValue, ignoreCase = true) ||
                    food.grams.contains(searchValue, ignoreCase = true) ||
                    food.calories.contains(searchValue, ignoreCase = true) ||
                    food.protein.contains(searchValue, ignoreCase = true) ||
                    food.satFat.contains(searchValue, ignoreCase = true) ||
                    food.fiber.contains(searchValue, ignoreCase = true) ||
                    food.carbs.contains(searchValue, ignoreCase = true) ||
                    food.category.contains(searchValue, ignoreCase = true)
        }
    }

    private fun buildSearchResultText(searchResults: List<Food>): String {
        val builder = StringBuilder()

        for (result in searchResults) {
            builder.append("Food: ${result.food}\n")
            builder.append("Measure: ${result.measure}\n")
            builder.append("Grams: ${result.grams}\n")
            builder.append("Calories: ${result.calories}\n")
            builder.append("Protein: ${result.protein}\n")
            builder.append("Sat.Fat: ${result.satFat}\n")
            builder.append("Fiber: ${result.fiber}\n")
            builder.append("Carbs: ${result.carbs}\n")
            builder.append("Category: ${result.category}\n\n")
        }

        return builder.toString()
    }

    data class Food(
        val food: String,
        val measure: String,
        val grams: String,
        val calories: String,
        val protein: String,
        val satFat: String,
        val fiber: String,
        val carbs: String,
        val category: String
    )
}

