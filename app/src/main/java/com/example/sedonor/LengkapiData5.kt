package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LengkapiData5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data5)
    }

    fun keLD6(view : View){
        val intentKeLD6 = Intent(this, LengkapiData6::class.java)
        startActivity(intentKeLD6)
    }
    fun back(view : View){
        val intentKeLD4 = Intent(this, LengkapiData3::class.java)
        startActivity(intentKeLD4)
    }
}