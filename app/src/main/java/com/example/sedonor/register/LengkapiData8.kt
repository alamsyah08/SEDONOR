package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sedonor.R

class LengkapiData8 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data8)
    }

    fun keLD9(view : View){
        val intentKeLD9 = Intent(this, LengkapiData9::class.java)
        startActivity(intentKeLD9)
    }
    fun back(view : View){
        val intentKeLD7 = Intent(this, LengkapiData7::class.java)
        startActivity(intentKeLD7)
    }
}