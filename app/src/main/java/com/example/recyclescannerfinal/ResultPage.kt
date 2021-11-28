package com.example.recyclescannerfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)

        val material = intent.getStringExtra("material").toString()
        var materialText = findViewById<TextView>(R.id.materialText)
        materialText.text = material


        var backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener()
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}