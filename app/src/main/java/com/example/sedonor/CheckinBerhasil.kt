package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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