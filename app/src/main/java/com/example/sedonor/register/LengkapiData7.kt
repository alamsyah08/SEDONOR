package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase

class LengkapiData7 : AppCompatActivity() {
    lateinit var pilihan: Spinner

    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data7)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()

        pilihan = findViewById<Spinner>(R.id.pilihGoldar)
        ArrayAdapter.createFromResource(
            this,
            R.array.goldar,
            android.R.layout.simple_spinner_item
        ).also {
                listPilihan -> listPilihan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            pilihan.adapter = listPilihan
        }
    }

    fun keLD8(view : View){
        var vPilihan = pilihan.selectedItem.toString()
        dbFirebase.updateData(retrievedUserId, "goldar", vPilihan, LengkapiData8::class.java)
    }
    fun back(view : View){
        val intentKeLD6 = Intent(this, LengkapiData6::class.java)
        startActivity(intentKeLD6)
    }
}