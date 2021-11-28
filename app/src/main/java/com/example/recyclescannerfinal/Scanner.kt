package com.example.recyclescannerfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.firebase.database.DatabaseReference
import java.io.File
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Scanner : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        var cancelButton = findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        database = FirebaseDatabase.getInstance("https://recyclingdata-2f543-default-rtdb.europe-west1.firebasedatabase.app").reference

        codeScanner()   // run the scanner

        var submitButton = findViewById<Button>(R.id.submit_scan_button)
        submitButton.setOnClickListener(){
            codeScanner.releaseResources()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //scanner function
    private fun codeScanner() {
        print(filesDir.toString())
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)

        //set focus, scan mode and flash
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            //when code is scanned
            decodeCallback = DecodeCallback {
                runOnUiThread {
                    val scannerText = findViewById<TextView>(R.id.scanner_text)
                    scannerText.text = it.text
                    var bcid = scannerText.text

                    var test = database.child("code").equalTo(bcid.toString())
                    print(test)

                }
            }

            //in case of errors
            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("main", "camera initialization error: ${it.message}")
                }
            }
        }

        //if scanner is set on "single" mode this is used to refresh the scanning function
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    //when app is focused resume scanning
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    //when app is not focused release resources
    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

}


