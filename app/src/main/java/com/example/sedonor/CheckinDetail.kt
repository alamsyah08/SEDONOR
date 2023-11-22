package com.example.sedonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CheckinDetail : AppCompatActivity() {
    private lateinit var tvNama: TextView
    private lateinit var tvLokasi: TextView
    private lateinit var gambarImageView: ImageView

    private lateinit var sessionManager: SessionManager
    private lateinit var dbFirebase: dbFirebase
    lateinit var retrievedUserId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin_detail)

        dbFirebase = dbFirebase(this)
        sessionManager = SessionManager(this)
        retrievedUserId = sessionManager.getUserId().toString()

//        val calendar = Calendar.getInstance()
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)

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
            val calender = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calender)
            dbFirebase.updateData(retrievedUserId, "checkin", dateFormat, CheckinBerhasil::class.java)
    }

    fun back(view : View){
        val intent = Intent(this, CheckinBerhasil::class.java)
        startActivity(intent)
    }



}