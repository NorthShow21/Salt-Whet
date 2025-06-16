package com.example.saltandwhet

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class DetailsActivity : AppCompatActivity() {
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        requestQueue = Volley.newRequestQueue(this)

        // Get the meal ID from the intent
        val mealId = intent.getStringExtra("mealId")
        if (mealId != null) {
            fetchMealDetails(mealId)
        }

        // Set up back button
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun fetchMealDetails(mealId: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val meals = response.getJSONArray("meals")
                    if (meals.length() > 0) {
                        val meal = meals.getJSONObject(0)
                        displayMealDetails(meal)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }
        )
        requestQueue.add(request)
    }

    private fun displayMealDetails(meal: JSONObject) {
        // Load image
        val imageUrl = meal.getString("strMealThumb")
        Glide.with(this)
            .load(imageUrl)
            .into(findViewById(R.id.ivRecipeImage))

        // Set recipe name
        findViewById<TextView>(R.id.tvRecipeName).text = meal.getString("strMeal")

        // Set area
        findViewById<TextView>(R.id.tvArea).text = meal.getString("strArea")

        // Set instructions
        findViewById<TextView>(R.id.tvInstructions).text = meal.getString("strInstructions")

        // Set ingredients
        val ingredients = StringBuilder()
        for (i in 1..20) {
            val ingredient = meal.optString("strIngredient$i")
            val measure = meal.optString("strMeasure$i")
            if (ingredient.isNotEmpty() && ingredient != "null") {
                ingredients.append("• $measure $ingredient\n")
            }
        }
        findViewById<TextView>(R.id.tvIngredients).text = ingredients.toString()
    }
}