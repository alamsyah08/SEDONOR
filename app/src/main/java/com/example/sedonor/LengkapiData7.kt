package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner

class LengkapiData7 : AppCompatActivity() {
    lateinit var pilihan: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data7)

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
        val intentKeLD8 = Intent(this, LengkapiData8::class.java)
        startActivity(intentKeLD8)
    }
    fun back(view : View){
        val intentKeLD6 = Intent(this, LengkapiData6::class.java)
        startActivity(intentKeLD6)
    }
}