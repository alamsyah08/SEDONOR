package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LengkapiData1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data1)
    }

    fun keLD2(view : View){
        val intentKeLD2 = Intent(this, LengkapiData2::class.java)
        startActivity(intentKeLD2)
    }
    fun back(view : View){
        val intentKeReg = Intent(this, Register::class.java)
        startActivity(intentKeReg)
    }
}