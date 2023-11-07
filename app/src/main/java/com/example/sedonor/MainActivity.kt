package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var email : EditText
    lateinit var pass : EditText
    lateinit var pesanSalah : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun keRegister(view: View){
        val intentKeRegister = Intent(this, Register::class.java)
        startActivity(intentKeRegister)
    }

    fun login(view: View){
        email = findViewById<EditText>(R.id.email)
        pass = findViewById<EditText>(R.id.inputPass)
        pesanSalah = findViewById<TextView>(R.id.pesanSalah)
        var salah ="Email atau Password salah"
        if(email.getText().toString() == "admin@gmail.com" && pass.getText().toString() == "123"){
            val intentKeRegister = Intent(this, HomePage::class.java)
            startActivity(intentKeRegister)
        }else{
            pesanSalah.text = salah
        }
    }

}