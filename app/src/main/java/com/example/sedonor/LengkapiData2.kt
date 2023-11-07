package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class LengkapiData2 : AppCompatActivity() {
    lateinit var pilihan: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data2)

        pilihan = findViewById<Spinner>(R.id.pilihGoldar)
        ArrayAdapter.createFromResource(
            this,
            R.array.gender,
            android.R.layout.simple_spinner_item
        ).also {
            listPilihan -> listPilihan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            pilihan.adapter = listPilihan
        }
    }

    fun keLD3(view : View){
        val intentKeLD3 = Intent(this, LengkapiData3::class.java)
        startActivity(intentKeLD3)
    }

    fun back(view : View){
        val intentKeLD1 = Intent(this, LengkapiData1::class.java)
        startActivity(intentKeLD1)
    }
}