package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase

class LengkapiData5 : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    lateinit var beratBadan : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data5)

        beratBadan = findViewById<EditText>(R.id.kodeDonor)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()

    }

    fun keLD6(view : View){
        var vBeratBadan: Int = Integer.parseInt(beratBadan.getText().toString())
        if(beratBadan.getText().isNotEmpty()){
            dbFirebase.updateData(retrievedUserId, "beratBadan", vBeratBadan, LengkapiData6::class.java)
        }else{
            beratBadan.requestFocus()
        }

    }
    fun back(view : View){
        val intentKeLD4 = Intent(this, LengkapiData3::class.java)
        startActivity(intentKeLD4)
    }
}