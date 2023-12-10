package com.example.sedonor.riwayat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.sedonor.R

class DetailRiwayatDonorActivity : AppCompatActivity() {
    private lateinit var tvJudul: TextView
    private lateinit var tvTanggal: TextView
    private lateinit var tvKH: TextView
    private lateinit var tvSH: TextView
    private lateinit var tvTDS: TextView
    private lateinit var tvTDD: TextView
    private lateinit var tvDN: TextView
    private lateinit var tvBB: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_riwayat_donor)

        tvJudul = findViewById(R.id.tvJudul)
        tvTanggal = findViewById(R.id.tvTanggal)
        tvKH = findViewById(R.id.tvKH)
        tvSH = findViewById(R.id.tvSH)
        tvTDS = findViewById(R.id.tvTDS)
        tvTDD = findViewById(R.id.tvTDD)
        tvDN = findViewById(R.id.tvDN)
        tvBB = findViewById(R.id.tvBB)

        // Get data from intent
        val intent = intent
        val judul = intent.getStringExtra("judul")
        val tanggal = intent.getStringExtra("tanggal")
        val kh = intent.getStringExtra("KH")
        val st = intent.getStringExtra("ST")
        val tdd = intent.getStringExtra("TDD")
        val tds = intent.getStringExtra("TDS")
        val dn = intent.getStringExtra("DN")
        val bb = intent.getStringExtra("BB")

        //setTextView
        tvJudul.text = judul
        tvTanggal.text = tanggal
        tvKH.text = kh
        tvSH.text = st
        tvTDS.text = tdd
        tvTDD.text = tds
        tvDN.text = dn
        tvBB.text = bb
    }

    fun btnBack (view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, RiwayatDonorActivity::class.java)
        startActivity(intent)
    }
}