package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LengkapiData3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data3)
    }

    fun keLD4(view : View){
        val intentKeLD4 = Intent(this, LengkapiData5::class.java)
        startActivity(intentKeLD4)
    }
    fun back(view : View){
        val intentKeLD2 = Intent(this, LengkapiData2::class.java)
        startActivity(intentKeLD2)
    }
}