package com.example.saltandwhet

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.splash_screen)
        val textView = findViewById<TextView>(R.id.textViewTitle)
        val styledText = "Find the perfect recipe with <font color='#FFEB3B'><b>Salt and Whet</b></font>"
        textView.text = Html.fromHtml("<b>$styledText</b>", Html.FROM_HTML_MODE_LEGACY)

        findViewById<Button>(R.id.btnGetStarted).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}