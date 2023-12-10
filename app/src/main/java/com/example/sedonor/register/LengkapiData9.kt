package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.sedonor.HomePage
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase

class LengkapiData9 : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    lateinit var kodeDonor : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data9)

        kodeDonor = findViewById<EditText>(R.id.kodeDonor)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()
    }

    fun keHome(view : View){
        var vKodeDonor: Int = Integer.parseInt(kodeDonor.getText().toString())
        if(kodeDonor.getText().isNotEmpty()){
            dbFirebase.updateData(retrievedUserId, "kodeDonor", vKodeDonor, HomePage::class.java)
        }else{
            kodeDonor.requestFocus()
        }
    }
    fun back(view : View){
        val intentKeLD8 = Intent(this, LengkapiData8::class.java)
        startActivity(intentKeLD8)
    }
}