package com.example.sedonor.scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sedonor.HomePage
import com.example.sedonor.R

class CheckinBerhasil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin_berhasil)
    }

    fun back(view : View){
        val intentKeLogin = Intent(this, HomePage::class.java)
        startActivity(intentKeLogin)
    }
}