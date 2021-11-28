package com.example.recyclescannerfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.recyclescannerfinal.Scanner

private const val CAMERA_REQUEST_CODE = 101

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPermissions() //check that we have camera permissions
        var scanButton = findViewById<Button>(R.id.scan_button)
        var quitButton = findViewById<Button>(R.id.quit_button)
        var formButton = findViewById<Button>(R.id.form_button)

        //set scan button to open the scanner
        scanButton.setOnClickListener(){
            val intent = Intent(this, Scanner::class.java)
            startActivity(intent)
        }

        //set form button to open the form // this is for testing purposes only
        formButton.setOnClickListener(){
            val intent2 = Intent(this, Form::class.java)
            startActivity(intent2)
        }
        //set quit button to exit application
        quitButton.setOnClickListener(){
            this.finishAffinity();
        }
    }


    // manual permission requests for newer androids
    private fun setupPermissions(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You need the camera permission to be able to use this app", Toast.LENGTH_SHORT)

                } else {
                    //success
                }
            }
        }
    }


}