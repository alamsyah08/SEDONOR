package com.example.sedonor.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import android.view.View
import com.bumptech.glide.Glide
import com.example.sedonor.HomePage
import com.example.sedonor.R

class ArtikelPage : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var myAdapter: MyAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artikel_page)

        bottomNavigationView = findViewById(R.id.bottom_navbar)

        setupBottomNavigationBar()

        val db = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.rvArtikel)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Mengambil data dari Firestore
        db.collection("artikels")

            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result.documents
                    // Konversi data Firestore ke list Artikel
                    val artikelList = convertQuerySnapshotToList(task.result)

                    // Inisialisasi dan atur adapter
                    myAdapter = MyAdapter(this, artikelList as ArrayList<Artikel>)

                    // Set listener untuk perpindahan halaman
                    myAdapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            // Dapatkan data artikel pada posisi yang diklik
                            val clickedArtikel = artikelList[position]

                            // Persiapkan intent untuk perpindahan halaman
                            val intent = Intent(this@ArtikelPage, DetailArtikel::class.java)
                            intent.putExtra("judul", clickedArtikel.judul)
                            intent.putExtra("konten", clickedArtikel.konten)
                            intent.putExtra("imageUrl", clickedArtikel.imageUrl)

//                            Log.w("imageUrl", clickedArtikel.imageUrl)
                            // Mulai aktivitas DetailArtikel
                            startActivity(intent)
                        }
                    })

                    // Set adapter ke RecyclerView
                    recyclerView.adapter = myAdapter
                } else {
                    // Handle kegagalan
                    Log.e("Firestore", "Error getting documents.", task.exception)
                }
            }
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


    private fun convertQuerySnapshotToList(querySnapshot: QuerySnapshot?): List<Artikel> {
        val artikelList = mutableListOf<Artikel>()
        querySnapshot?.forEach { document: QueryDocumentSnapshot ->
            // Retrieve data from Firestore document
            val judul = document.getString("judul")
            val konten = document.getString("konten")
            val imageUrl = document.getString("gambar")
            val artikel = Artikel(judul, konten, imageUrl)
            artikelList.add(artikel)
        }
        return artikelList
    }

    fun intentKeHome (view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }

    private fun showToastAndNavigate(message: String, destination: Class<*>) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, destination))
    }

}
