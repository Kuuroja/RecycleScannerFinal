package com.example.recyclescannerfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.recyclescannerfinal.databinding.ActivityFormBinding
import com.example.recyclescannerfinal.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Form : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        //database = Firebase.database("https://recyclingdata-2f543-default-rtdb.europe-west1.firebasedatabase.app").reference

        var saveBtn = findViewById<Button>(R.id.save_btn)
        var bcidText = findViewById<EditText>(R.id.bcidText)
        var pName = findViewById<EditText>(R.id.product_name_input)
        var pMaterial = findViewById<Spinner>(R.id.product_material_spinner)
        var cancelBtn = findViewById<Button>(R.id.cancel_btn)

        val spinner: Spinner = pMaterial
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.recycle_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        // set save button to send data to database
        saveBtn.setOnClickListener {

            val code = bcidText.text.toString()
            val productName = pName.text.toString()
            val productMaterial = pMaterial.selectedItem.toString()

            database = FirebaseDatabase.getInstance("https://recyclingdata-2f543-default-rtdb.europe-west1.firebasedatabase.app").reference
            database.child(code).setValue(RecycleData(productName, productMaterial))
            val intent = Intent(this, ResultPage::class.java)
            intent.putExtra("material", productMaterial)
            startActivity(intent)

        }

        //set cancel button to open the main menu
        cancelBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}