package com.example.sedonor.scanner

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.sedonor.HomePage
import com.example.sedonor.R
import com.example.sedonor.SessionManager
import com.example.sedonor.Users
import com.example.sedonor.dbFirebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CheckinDetail : AppCompatActivity() {
    private lateinit var tvNama: TextView
    private lateinit var tvLokasi: TextView
    private lateinit var gambarImageView: ImageView

    private lateinit var sessionManager: SessionManager
    val db = FirebaseFirestore.getInstance()
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin_detail)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()

        tvNama = findViewById<TextView>(R.id.tvNama)
        tvLokasi = findViewById<TextView>(R.id.tvLokasi)
        gambarImageView = findViewById(R.id.imgView)

        val nama = intent.getStringExtra("NAMADONOR")
        val lokasi = intent.getStringExtra("ALAMATLOKASI")
        val foto = intent.getStringExtra("IMAGELOKASI")

        tvNama.text = nama
        tvLokasi.text = lokasi
        Glide.with(this)
            .load(foto)
            .placeholder(R.drawable.ic_back) //cari gambar loading
            .into(gambarImageView)
    }

    fun btnCheckin(view: View){
        db.collection("users").document(retrievedUserId)
            .get()
            .addOnCompleteListener { task ->
                val user = convertDocumentSnapshotToUser(task.result)
                if(user?.status == false){
                    val calender = Calendar.getInstance().time
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calender)
                    setStatus()
                    setRiwayatStatus(dateFormat)
                    dbFirebase.updateData(retrievedUserId, "checkin", dateFormat, CheckinBerhasil::class.java)
                }else{
                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                }
            }
    }

    fun setStatus(){
        val db = db.collection("users").document(retrievedUserId)
        db.update("status", true)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "status Users update success")
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error status Users update document", e) }
    }

    fun setRiwayatStatus(doc: String){
        val db = db.collection("users").document(retrievedUserId).collection("riwayat").document(doc)
        db.update("status", true)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "status Riwayat update success")
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error status Riwayat update document", e) }
    }

    private fun convertDocumentSnapshotToUser(documentSnapshot: DocumentSnapshot): Users? {
        return documentSnapshot.toObject(Users::class.java)
    }

    fun back(view : View){
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }



}