package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LengkapiData6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data6)
    }

    fun keLD7(view : View){
        val intentKeLD7 = Intent(this, LengkapiData7::class.java)
        startActivity(intentKeLD7)
    }
    fun back(view : View){
        val intentKeLD5 = Intent(this, LengkapiData5::class.java)
        startActivity(intentKeLD5)
    }
}