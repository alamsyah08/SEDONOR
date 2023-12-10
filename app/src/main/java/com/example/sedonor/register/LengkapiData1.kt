package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase
import com.google.firebase.*
import com.google.firebase.auth.*
import com.google.firebase.firestore.firestore

class LengkapiData1 : AppCompatActivity() {
    lateinit var nama : EditText

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore

    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data1)

        nama = findViewById<EditText>(R.id.nama)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()
    }

    fun keLD2(view : View){
        var vNama = nama.getText().toString()
        if(nama.getText().isNotEmpty()){
            dbFirebase.updateData(retrievedUserId, "nama", vNama, LengkapiData2::class.java)
        }else{
            nama.requestFocus()
        }

    }
    fun back(view : View){
        val intentKeReg = Intent(this, Register::class.java)
        startActivity(intentKeReg)
    }

//    fun saveData(uID: String, nama: HashMap<String, String>){
//        firestore.collection("users").document(uID).set(nama)
//            .addOnSuccessListener {
//                Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()
//                val intentKeLD2 = Intent(this, LengkapiData2::class.java)
//                startActivity(intentKeLD2)
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(baseContext, "Firestore error: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }

//    fun updateData(uID : String, nama: String){
//        val db = firestore.collection("users").document(uID)
//        db.update("nama", nama)
//            .addOnSuccessListener {
//                Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()
//                val intentKeLD2 = Intent(this, LengkapiData2::class.java)
//                startActivity(intentKeLD2)}
//            .addOnFailureListener { e -> Toast.makeText(baseContext, "Firestore error: ${e.message}", Toast.LENGTH_SHORT).show() }
//    }

}