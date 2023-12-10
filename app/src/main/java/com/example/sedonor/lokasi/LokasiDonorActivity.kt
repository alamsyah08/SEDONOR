package com.example.sedonor.lokasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sedonor.HomePage
import com.example.sedonor.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class LokasiDonorActivity : AppCompatActivity() {
    private lateinit var myAdapter: MyAdapterLokasi
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lokasi_donor)

        val db = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.rvArtikel)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Mengambil data dari Firestore
        db.collection("tempat")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result.documents
                    Log.e("data", document.toString())
                    // Konversi data Firestore ke list Artikel
                    val lokasiList = convertQuerySnapshotToList(task.result)

                    // Inisialisasi dan atur adapter
                    myAdapter = MyAdapterLokasi(this, lokasiList as ArrayList<LokasiDonor>)

                    // Set listener untuk perpindahan halaman
                    myAdapter.setOnItemClickListener(object : MyAdapterLokasi.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            // Dapatkan data artikel pada posisi yang diklik
                            val clickedArtikel = lokasiList[position]

                            // Persiapkan intent untuk perpindahan halaman
                            val intent = Intent(this@LokasiDonorActivity, DetailLokasiActivity::class.java)
                            intent.putExtra("nama", clickedArtikel.nama)
                            intent.putExtra("lokasi", clickedArtikel.lokasi)
                            intent.putExtra("deskripsi", clickedArtikel.deskripsi)
                            intent.putExtra("foto", clickedArtikel.foto)

//                             Mulai aktivitas DetailArtikel
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

    private fun convertQuerySnapshotToList(querySnapshot: QuerySnapshot?): List<LokasiDonor> {
        val riwayatList = mutableListOf<LokasiDonor>()
        querySnapshot?.forEach { document: QueryDocumentSnapshot ->
            // Retrieve data from Firestore document
            val deskripsi = document.getString("deskripsi")
            val foto = document.getString("foto")
            val lokasi = document.getString("lokasi")
            val nama = document.getString("nama")
            val riwayat = LokasiDonor(deskripsi.toString(), foto.toString(),lokasi.toString(), nama.toString())
            riwayatList.add(riwayat)
        }
        return riwayatList
    }

    fun intentKeHome (view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
}