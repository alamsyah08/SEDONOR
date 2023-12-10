package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase
import com.example.sedonor.login.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Register : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var pass: EditText
    lateinit var konfPass: EditText
    lateinit var checkBox: CheckBox
    lateinit var pesan: TextView

    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase

    private lateinit var auth: FirebaseAuth
    private val firestore = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbFirebase = dbFirebase(this)
        auth = Firebase.auth
        sessionManager = SessionManager(this)

        email = findViewById<EditText>(R.id.email)
        pass = findViewById<EditText>(R.id.pass)
        konfPass = findViewById<EditText>(R.id.konfPass)
        checkBox = findViewById<CheckBox>(R.id.cbSK)
        pesan = findViewById<TextView>(R.id.pesanSalah)
    }

    fun keLengkapiData(view: View){
        var vEmail = email.getText().toString()
        var vPass = pass.getText().toString()
        var vKonfPass = konfPass.getText().toString()
        if(email.getText().isNotEmpty() && pass.getText().isNotEmpty() && konfPass.getText().isNotEmpty()){
                if(vPass.equals(vKonfPass)){
                    if(checkBox.isChecked()) {
                        dbFirebase.RegisterFirebase(vEmail, vPass)
                    }else{
                        pesan.setText("S&K belum disetujui")
                    }
                }else{
                    pesan.setText("Konfirmasi password berbeda")
                }
        }else{
            pesan.setText("Email atau Password tidak Boleh Kosong")
        }
    }

    fun keLogin(view : View){
        val intentKeLogin = Intent(this, MainActivity::class.java)
        startActivity(intentKeLogin)
    }

//    private fun RegisterFirebase(email: String, password: String){
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(baseContext, "Register Berhasil", Toast.LENGTH_SHORT).show()
//                    val user = Firebase.auth.currentUser
//                    val userId = user?.uid.toString()
//                    if (userId != null) {
//                        sessionManager.saveUserId(userId)
//                    }
//                    val userData = hashMapOf<String, Any?>(
//                        "nama" to "null",
//                        "gender" to null,
//                        "tanggalLahir" to null,
//                        "domisili" to null,
//                        "tinggiBadan" to 0,
//                        "beratBadan" to 0,
//                        "goldar" to null,
//                        "riwayatPenyakit" to null
//                    )
//
//                    saveData(userId, userData)
//
//                    val intentKeLD1 = Intent(this, LengkapiData1::class.java)
//                    startActivity(intentKeLD1)
//                } else {
//                    Toast.makeText(baseContext, "Register Gagal.",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    fun saveData(uID: String, nama: HashMap<String, Any?>){
//        firestore.collection("users").document(uID.toString()).set(nama)
//            .addOnSuccessListener {
//                Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()
//                val intentKeLD1 = Intent(this, LengkapiData1::class.java)
//                startActivity(intentKeLD1)
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(baseContext, "Firestore error: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
}