package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

class Register : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var pass: EditText
    lateinit var konfPass: EditText
    lateinit var checkBox: CheckBox
    lateinit var pesan: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun keLengkapiData(view: View){
        email = findViewById<EditText>(R.id.email)
        pass = findViewById<EditText>(R.id.pass)
        konfPass = findViewById<EditText>(R.id.konfPass)
        checkBox = findViewById<CheckBox>(R.id.cbSK)
        pesan = findViewById<TextView>(R.id.pesanSalah)

        if(email.getText().isNotEmpty() && pass.getText().isNotEmpty() && konfPass.getText().isNotEmpty()){
            if(pass.getText().toString() == konfPass.getText().toString()){
                if(checkBox.isChecked()) {
                    val intentKeLD1 = Intent(this, LengkapiData1::class.java)
                    startActivity(intentKeLD1)
                }else{
                    pesan.setText("S&K belum disetujui")
                }
            }else{
                pesan.setText("Konfirmasi password berbeda")
            }
        }else{
            pesan.setText("Tidak Boleh Kosong")
        }
    }

    fun keLogin(view : View){
        val intentKeLogin = Intent(this, MainActivity::class.java)
        startActivity(intentKeLogin)
    }
}