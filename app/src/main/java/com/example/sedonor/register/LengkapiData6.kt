package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase

class LengkapiData6 : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    lateinit var tinggiBadan : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data6)

        tinggiBadan = findViewById<EditText>(R.id.tinggiBadan)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()
    }

    fun keLD7(view : View){
        var vTinggiBadan: Int = Integer.parseInt(tinggiBadan.getText().toString())
        if(tinggiBadan.getText().isNotEmpty()){
            dbFirebase.updateData(retrievedUserId, "tinggiBadan", vTinggiBadan, LengkapiData7::class.java)
        }else{
            tinggiBadan.requestFocus()
        }
    }
    fun back(view : View){
        val intentKeLD5 = Intent(this, LengkapiData5::class.java)
        startActivity(intentKeLD5)
    }
}