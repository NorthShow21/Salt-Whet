package com.example.saltandwhet

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.rv_item_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        requestQueue = Volley.newRequestQueue(this)

        val url = "https://www.themealdb.com/api/json/v1/1/search.php?f=p"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null, { respone ->
                try{
                    val itemList = parseJSON(respone)
                    val adapter = RecipeAdapter(itemList)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, { error ->
                Log.e("Volley error", error.toString())
            }
        )
        requestQueue.add(request)
    }

    private fun parseJSON(jsonObject: JSONObject):ArrayList<Recipe> {
        val itemList = ArrayList<Recipe>()
        try {
            val itemArray = jsonObject.getJSONArray("meals")
            for (i in 0 until itemArray.length()){
                val itemObject = itemArray.getJSONObject(i)
                val itemImage = itemObject.getString("strMealThumb")
                val itemName = itemObject.getString("strMeal")
                val itemArea = itemObject.getString("strArea")
                itemList.add(Recipe(itemImage, itemName, itemArea))
            }
        } catch(e: JSONException) {
            e.printStackTrace()
        }
        return itemList
    }
}