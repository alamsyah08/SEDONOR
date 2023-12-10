package com.example.sedonor.riwayat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.sedonor.R

class PoinDonorActivity : AppCompatActivity() {
    private lateinit var tvLevel : TextView
    private lateinit var imgLevel : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poin_donor)

        tvLevel = findViewById(R.id.tvLevelPoin)
        imgLevel = findViewById(R.id.imgLevelPoin)
        val total = intent.getStringExtra("JMLHDONOR")

        if (total != null) {
            if(total.toInt() > 25){
                val newBackgroundImage = ContextCompat.getDrawable(this, R.drawable.bg_platinum)
                imgLevel.setImageDrawable(newBackgroundImage)
                tvLevel.text = "Platinum"
            }else if(total.toInt() > 10){
                val newBackgroundImage = ContextCompat.getDrawable(this, R.drawable.bg_emas)
                imgLevel.setImageDrawable(newBackgroundImage)
                tvLevel.text = "Emas"
            }else if(total.toInt() > 5){
                val newBackgroundImage = ContextCompat.getDrawable(this, R.drawable.bg_silver)
                imgLevel.setImageDrawable(newBackgroundImage)
                tvLevel.text = "Perak"
            }else if(total.toInt() >  1){
                val newBackgroundImage = ContextCompat.getDrawable(this, R.drawable.bg_perunggu)
                imgLevel.setImageDrawable(newBackgroundImage)
                tvLevel.text = "Perunggu"
            }
        }
    }

    fun intentBack(view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, RiwayatDonorActivity::class.java)
        startActivity(intent)
    }
}