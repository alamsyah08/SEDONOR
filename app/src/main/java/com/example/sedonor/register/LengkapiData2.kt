package com.example.sedonor.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.dbFirebase

class LengkapiData2 : AppCompatActivity() {
    lateinit var pilihan: Spinner

    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_data2)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()

        pilihan = findViewById<Spinner>(R.id.pilihGoldar)
        ArrayAdapter.createFromResource(
            this,
            R.array.gender,
            android.R.layout.simple_spinner_item
        ).also {
            listPilihan -> listPilihan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            pilihan.adapter = listPilihan
        }
    }

    fun keLD3(view : View){
        var vPilihan = pilihan.selectedItem.toString()
        dbFirebase.updateData(retrievedUserId, "gender", vPilihan, LengkapiData3::class.java)
    }

    fun back(view : View){
        val intentKeLD1 = Intent(this, LengkapiData1::class.java)
        startActivity(intentKeLD1)
    }

//    fun updateData(uID : String, nama: String){
//        val db = firestore.collection("users").document(uID)
//        db.update("gender", nama)
//            .addOnSuccessListener {
//                Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()
//                val intentKeLD2 = Intent(this, LengkapiData3::class.java)
//                startActivity(intentKeLD2)}
//            .addOnFailureListener { e -> Toast.makeText(baseContext, "Firestore error: ${e.message}", Toast.LENGTH_SHORT).show() }
//    }

//    fun saveData(uID: String, nama: HashMap<String, String>){
//        firestore.collection("users").document(uID).set(nama)
//            .addOnSuccessListener {
//                Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()
//                val intentKeLD3 = Intent(this, LengkapiData3::class.java)
//                startActivity(intentKeLD3)
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(baseContext, "Firestore error: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }

}