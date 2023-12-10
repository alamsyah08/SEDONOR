package com.example.sedonor.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.sedonor.R
import com.example.sedonor.register.Register
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase
import com.google.firebase.Firebase
import com.google.firebase.auth.*

class MainActivity : AppCompatActivity() {
    lateinit var email : EditText
    lateinit var pass : EditText
    lateinit var pesanSalah : TextView

    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById<EditText>(R.id.email)
        pass = findViewById<EditText>(R.id.inputPass)
        pesanSalah = findViewById<TextView>(R.id.pesanSalah)

        dbFirebase = dbFirebase(this)
        auth = Firebase.auth
        sessionManager = SessionManager(this)
    }

    fun keRegister(view: View){
        val intentKeRegister = Intent(this, Register::class.java)
        startActivity(intentKeRegister)
    }

    fun login(view: View){
        var vEmail = email.getText().toString()
        var vPass = pass.getText().toString()
        var salah ="Email atau Password belum diisi"

        if(email.getText().isEmpty() || pass.getText().isEmpty()){
            pesanSalah.text = salah
        }else{
            dbFirebase.LoginFirebase(vEmail, vPass)
        }
    }

//
//    private fun LoginFirebase(email: String, password: String){
//        var salah ="Email atau Password salah"
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(baseContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
//                    val user: FirebaseUser? = auth.currentUser
//                    val userId = user?.uid
//                    if (userId != null) {
//                        sessionManager.saveUserId(userId)
//                    }
//                    val intentKeRegister = Intent(this, HomePage::class.java)
//                    startActivity(intentKeRegister)
//                } else {
//                    Toast.makeText(baseContext, salah,
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

}