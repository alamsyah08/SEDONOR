package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase

class LengkapiData4 : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    lateinit var domisili : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data4)

        domisili = findViewById<EditText>(R.id.domisili)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()
    }

    fun keLD5(view : View){
        var vDomisili = domisili.getText().toString()
        if(domisili.getText().isNotEmpty()){
            dbFirebase.updateData(retrievedUserId, "domisili", vDomisili, LengkapiData5::class.java)
        }else{
            domisili.requestFocus()
        }
    }

    fun back(view : View){
        val intentKeLD3 = Intent(this, LengkapiData3::class.java)
        startActivity(intentKeLD3)
    }
}