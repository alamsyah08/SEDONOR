package com.example.sedonor.lokasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.sedonor.R

class DetailLokasiActivity : AppCompatActivity() {
    private lateinit var tvNama: TextView
    private lateinit var tvLokasi: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var tvImg: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lokasi)

        tvImg = findViewById<ImageView>(R.id.imgView)
        tvNama = findViewById<TextView>(R.id.tvNama)
        tvLokasi = findViewById<TextView>(R.id.tvLokasi)
        tvDeskripsi = findViewById<TextView>(R.id.tvKontenDeskripsi)

        // Get data from intent
        val intent = intent
        val nama = intent.getStringExtra("nama")
        val lokasi = intent.getStringExtra("lokasi")
        val image = intent.getStringExtra("foto")
        val deskripsi = intent.getStringExtra("deskripsi")

        tvNama.text = nama
        tvLokasi.text = lokasi
        tvDeskripsi.text = deskripsi
        Glide.with(this)
            .load(image)
            .placeholder(R.drawable.ic_back) //cari gambar loading
            .into(tvImg)


    }

    fun intentBack (view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, LokasiDonorActivity::class.java)
        startActivity(intent)
    }
}