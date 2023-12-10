package com.example.sedonor.artikel

import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.sedonor.HomePage
import com.example.sedonor.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailArtikel : AppCompatActivity() {

    private lateinit var judulTextView: TextView
    private lateinit var kontenTextView: TextView
    private lateinit var gambarImageView: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)

        bottomNavigationView = findViewById(R.id.bottom_navbar)

        setupBottomNavigationBar()

        // Initialize views
        judulTextView = findViewById(R.id.tvTitleDetailArtikel)
        kontenTextView = findViewById(R.id.tvKontenDetailArtikel)
        gambarImageView = findViewById(R.id.ivDetailArtikel)

        // Get data from intent
        val intent = intent
        val judul = intent.getStringExtra("judul")
        val konten = intent.getStringExtra("konten")
        val imageUrl = intent.getStringExtra("imageUrl")

        // Set data to TextViews and ImageView
        judulTextView.text = judul
        kontenTextView.text = konten

        // Use Glide to load the image from the URL into the ImageView
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.loading) // You can set a placeholder image
            .into(gambarImageView)

        // If judul is available, fetch additional details from Firestore
//        if (judul != null) {
//            fetchAdditionalDetails(judul)
//        }
    }

    private fun setupBottomNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            handleBottomNavigationItemClick(item)
        }
    }

    private fun handleBottomNavigationItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_1 -> {
                // Ganti warna ikon untuk Home
                item.setIcon(R.drawable.ic_home_selected)
                // Lakukan aksi atau pindah ke halaman Home
                showToastAndNavigate("Home Clicked", HomePage::class.java)
            }
            R.id.menu_item_2 -> {
                // Ganti warna ikon untuk Tanya PMI
                item.setIcon(R.drawable.ic_tanyapmi_selected)
                // Lakukan aksi atau pindah ke halaman Tanya PMI
//                showToastAndNavigate("Tanya PMI Clicked", TanyaPMI::class.java)
            }
            R.id.menu_item_3 -> {
                // Ganti warna ikon untuk Account
                item.setIcon(R.drawable.ic_akun_selected)
                // Lakukan aksi atau pindah ke halaman Account
//                showToastAndNavigate("Account Clicked", Account::class.java)
            }
        }
        return true
    }

    fun intentKeListArtikel (view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, ArtikelPage::class.java)
        startActivity(intent)
    }

    private fun showToastAndNavigate(message: String, destination: Class<*>) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, destination))
    }

//    private fun fetchAdditionalDetails(judul: String) {
//        // Fetch data from Firestore based on the article title
//        val db = FirebaseFirestore.getInstance()
//        val artikelRef = db.collection("artikels")
//            .whereEqualTo("judul", judul)
//            .limit(1) // Assuming the title is unique
//
//        artikelRef.get()
//            .addOnSuccessListener { querySnapshot ->
//                if (!querySnapshot.isEmpty) {
//                    val document = querySnapshot.documents[0]
//
//                    // Retrieve data from Firestore document
//                    val judul = document.getString("judul")
//                    val konten = document.getString("konten")
//                    val imageUrl = document.getString("gambar")
//
//                    // Update TextViews and ImageView with additional details
//                    judulTextView.text = judul
//                    kontenTextView.text = konten
//
//                    Glide.with(this)
//                        .load(imageUrl)
//                        .placeholder(R.drawable.test) //cari gambar loading
//                        .into(gambarImageView)
//                } else {
//                    // Handle the case where no document matches the query
//                }
//            }
//            .addOnFailureListener { exception ->
//                // Handle failures
//            }
//    }
}
